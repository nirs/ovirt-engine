package org.ovirt.engine.ui.webadmin.widget.table.column;

import java.util.List;

import org.ovirt.engine.core.common.businessentities.gluster.GlusterVolumeEntity;

import com.google.gwt.cell.client.HasCell;
import com.google.gwt.user.cellview.client.Column;

public class VolumeActivityColumn extends Column<GlusterVolumeEntity, GlusterVolumeEntity> {

    public VolumeActivityColumn(List<HasCell<GlusterVolumeEntity, ?>> list) {
        super(new VolumeActivityCompositeCell<GlusterVolumeEntity>(list));
    }

    @Override
    public GlusterVolumeEntity getValue(GlusterVolumeEntity object) {
        return object;
    }
}
