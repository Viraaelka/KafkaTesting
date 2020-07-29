package objects.db;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DbDefinition {

    String name;
    DbType type;
    String server;
    String port;
    String database;
    String user;
    String password;

    public String getName() {
        return name;
    }

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public DbType getType() {
        return type;
    }

    @XmlAttribute
    public void setType(DbType type) {
        this.type = type;
    }

    public String getDatabase() {
        return database;
    }

    @XmlElement
    public void setDatabase(String database) {
        this.database = database;
    }

    public String getServer() {
        return server;
    }

    @XmlElement
    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    @XmlElement
    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    @XmlElement
    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "name: "+getName()+" type: "+getType()+" server: "+getServer()+" port: "+getPort()+" database: "+getDatabase()+" user: "+ getUser()+" password: "+getPassword();
    }

    @Override
    public boolean equals(Object obj){
        DbDefinition def = (DbDefinition)obj;
        return this.name.equals(def.getName()) && this.type.equals(def.getType());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode() + getType().hashCode();
        return result;
    }

}
