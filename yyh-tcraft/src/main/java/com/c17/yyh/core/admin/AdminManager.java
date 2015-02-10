package com.c17.yyh.core.admin;

import com.c17.yyh.core.admin.dao.IAdminConfigDao;
import com.c17.yyh.core.admin.dao.entities.AdminUser;
import com.c17.yyh.core.admin.type.AdminCommandType;
import com.c17.yyh.core.admin.type.Command;
import com.c17.yyh.core.admin.type.GrantCallback;
import com.c17.yyh.core.admin.type.GrantLevelType;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.exceptions.ServerException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

@Service("adminManager")
public class AdminManager {

    @Autowired
    private IAdminConfigDao adminConfig;

    @Autowired
    private ApplicationContext applicationContext;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final HashMap<String, AdminUser> users = new HashMap<>();
    private final Map<String, Command> adminCommands = new HashMap<>();
    private AdminCredentials currentUserCredentials;

    @PostConstruct
    protected void initialize() {
        initUsers();
        initAdminCommands();
    }

    private void initUsers() {
        List<AdminUser> configuredUsers = adminConfig.getUsers();
        for (AdminUser user : configuredUsers) {
            users.put(user.getLogin(), user);
        }
        logger.info("Loaded {} admin users", users.keySet().size());
    }

    @SuppressWarnings("unchecked")
	private void initAdminCommands() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(AdminCommandType.class));
        for (BeanDefinition bd : scanner.findCandidateComponents(Command.class.getPackage().getName())) {
            String className = bd.getBeanClassName();
            Class<Command> adminCommandClass;
            try {
                adminCommandClass = (Class<Command>) Class.forName(className);
            } catch (ClassNotFoundException e) {
                logger.error("Can't find class " + className, e);
                continue;
            }
            AdminCommandType ann = adminCommandClass.getAnnotation(AdminCommandType.class);
            adminCommands.put(ann.name(), new AdminCommandWrapper(applicationContext.getBean(adminCommandClass), ann.level(), new GrantCallback() {

                @Override
                public boolean isAccessAllowed(GrantLevelType level) {
                    AdminUser checkedUser = users.get(currentUserCredentials.getLogin());
                    return checkedUser != null && checkedUser.getPassword().equals(currentUserCredentials.getPassword()) && checkedUser.getLevel().ordinal() <= level.ordinal();
                }
            }));

        }
        logger.info("Loaded {} admin commands", adminCommands.keySet().size());
    }

    public void executeAdminCommand(String command, AdminCredentials credentials, Map<String, String> data) {
        Command adminCommand = adminCommands.get(command);
        if (adminCommand == null) {
            throw new ServerException(ErrorCodes.INCORRECT_COMMAND, "Admin command doesn't exist", true);
        }
        currentUserCredentials = credentials;
        adminCommand.execute(data);
    }

    public class AdminCredentials {

        private final String login;
        private final String password;

        public AdminCredentials(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

    }

}
