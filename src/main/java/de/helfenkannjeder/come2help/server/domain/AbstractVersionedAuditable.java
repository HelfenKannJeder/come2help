package de.helfenkannjeder.come2help.server.domain;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractVersionedAuditable extends AbstractAuditable implements Cloneable {

    @Version
    @Basic(optional = false)
    private int version = 0;

    public int getVersion() {
        return version;
    }

    public void setValues(AbstractVersionedAuditable newValues) {
        version = newValues.version;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final AbstractVersionedAuditable newObject = (AbstractVersionedAuditable) super.clone();
        newObject.version = getVersion();
        return newObject;
    }

    @Override
    public String toString() {
        return "AbstractVersionedAuditable [version=" + version + ", " + super.toString() + "]";
    }
}
