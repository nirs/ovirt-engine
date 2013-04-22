package org.ovirt.engine.core.dal.dbbroker.auditloghandling;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.ovirt.engine.core.utils.cache.CacheManager;

public abstract class TimeoutBase implements Serializable {
    private static final long serialVersionUID = -4969034051659487755L;
    private boolean mUseTimeout;
    private long mEndTime = 0L;

    public boolean getUseTimout() {
        return mUseTimeout;
    }

    public void setUseTimout(boolean value) {
        mUseTimeout = value;
    }

    public long getEndTime() {
        return mEndTime;
    }

    public void setEndTime(long value) {
        mUseTimeout = true;
        mEndTime = value;
    }

    private String timeoutObjectId = "";

    protected abstract String getKey();

    private String getkeyForCheck() {
        return "".equals(getTimeoutObjectId()) ? getKey() : String.format("%1$s_%2$s", getKey(), getTimeoutObjectId());
    }

    public String getTimeoutObjectId() {
        return timeoutObjectId;
    }

    public void setTimeoutObjectId(String value) {
        timeoutObjectId = value;
    }

    /**
     * Checks if timeout is used and if it is, checks the timeout. If no timeout set, then it will set this object as timeout.
     * @return should the action be logged again
     */
    @SuppressWarnings("unchecked")
    public boolean getLegal() {
        if (getUseTimout()) {
            String keyForCheck = getkeyForCheck();
            if (CacheManager.getTimeoutBaseCache().putIfAbsent(keyForCheck,
                    this,
                    this.getEndTime(),
                    TimeUnit.MILLISECONDS) == null) {
                return true;
            }
            return false;
        }

        return true;
    }
}
