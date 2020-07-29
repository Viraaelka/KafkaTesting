package objects.db;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DbType")
@XmlEnum
public enum DbType {
    MSSQLSERVER,
    ORACLE,
    NOSQL;
}