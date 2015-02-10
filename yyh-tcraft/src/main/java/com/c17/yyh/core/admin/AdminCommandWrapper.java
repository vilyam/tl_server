package com.c17.yyh.core.admin;

import java.util.Map;

import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.core.admin.type.Command;
import com.c17.yyh.core.admin.type.GrantCallback;
import com.c17.yyh.core.admin.type.GrantLevelType;
import com.c17.yyh.exceptions.ServerException;

/**
 *
 * @author sigurd
 */
public class AdminCommandWrapper extends Command {

    private final Command adminCommand;
    private final GrantLevelType grantLevel;
    private final GrantCallback grantCallback;

    public AdminCommandWrapper(Command adminCommand, GrantLevelType grantLevel, GrantCallback grantCallback) {
        this.adminCommand = adminCommand;
        this.grantLevel = grantLevel;
        this.grantCallback = grantCallback;
    }

    @Override
    public void execute(Map<String, String> data) {
        if (!grantCallback.isAccessAllowed(grantLevel)) {
            throw new ServerException(ErrorCodes.ADMIN.OPERATION_FORBIDDEN, "User doesn't exist or has no rights to execute command", true);
        }
        adminCommand.execute(data);
    }

}
