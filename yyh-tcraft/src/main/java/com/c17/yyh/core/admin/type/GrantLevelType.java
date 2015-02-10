package com.c17.yyh.core.admin.type;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author sigurd
 */

@XmlType(name = "level")
@XmlEnum
public enum GrantLevelType {
    ADMIN, SUPPORT
}
