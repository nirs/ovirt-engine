package org.ovirt.engine.core.dao;

import java.util.List;

import org.ovirt.engine.core.common.businessentities.VmJob;
import org.ovirt.engine.core.compat.Guid;

public interface VmJobDao extends GenericDao<VmJob, Guid>, MassOperationsDao<VmJob, Guid> {
    /*
     * Retrieves a list of VmJobs associated with a VM disk image
     *
     * @param vm
     *      The Vm UUID
     * @param image
     *      The Image UUID
     *
     * @return A list of Vm Jobs
     */
    List<VmJob> getAllForVmDisk(Guid vm, Guid image);

}
