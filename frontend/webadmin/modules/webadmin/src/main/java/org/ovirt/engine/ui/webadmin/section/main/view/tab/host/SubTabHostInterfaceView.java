package org.ovirt.engine.ui.webadmin.section.main.view.tab.host;

import javax.inject.Inject;

import org.ovirt.engine.core.common.businessentities.VDS;
import org.ovirt.engine.ui.uicommonweb.models.hosts.HostInterfaceLineModel;
import org.ovirt.engine.ui.uicommonweb.models.hosts.HostInterfaceListModel;
import org.ovirt.engine.ui.uicommonweb.models.hosts.HostListModel;
import org.ovirt.engine.ui.webadmin.section.main.presenter.tab.host.SubTabHostInterfacePresenter;
import org.ovirt.engine.ui.webadmin.section.main.view.AbstractSubTabFormView;
import org.ovirt.engine.ui.webadmin.section.main.view.AbstractSubTabTableView.SubTableResources;
import org.ovirt.engine.ui.webadmin.uicommon.model.SearchableDetailModelProvider;
import org.ovirt.engine.ui.webadmin.widget.host.HostInterfaceForm;
import org.ovirt.engine.ui.webadmin.widget.table.ActionTableDataProvider;
import org.ovirt.engine.ui.webadmin.widget.table.SimpleActionTable;
import org.ovirt.engine.ui.webadmin.widget.table.UiCommandButtonDefinition;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable.Resources;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class SubTabHostInterfaceView extends AbstractSubTabFormView<VDS, HostListModel, HostInterfaceListModel>
        implements SubTabHostInterfacePresenter.ViewDef {

    /**
     * A Column for an empty Grid
     */
    private class EmptyColumn extends TextColumn<HostInterfaceLineModel> {
        @Override
        public String getValue(HostInterfaceLineModel object) {
            return null;
        }
    }

    /**
     * A Provider for an empty grid
     */
    private class EmptyProvider extends ListDataProvider<HostInterfaceLineModel>
            implements ActionTableDataProvider<HostInterfaceLineModel> {

        @Override
        public boolean canGoForward() {
            return false;
        }

        @Override
        public boolean canGoBack() {
            return false;
        }

        @Override
        public void goForward() {
        }

        @Override
        public void goBack() {
        }

    }

    private final SimpleActionTable<HostInterfaceLineModel> table;

    private final VerticalPanel contentPanel;

    @Inject
    public SubTabHostInterfaceView(SearchableDetailModelProvider<HostInterfaceLineModel, HostListModel, HostInterfaceListModel> modelProvider) {
        super(modelProvider);
        this.table = createEmptyTable(createEmptyProvider());
        initTable();

        contentPanel = new VerticalPanel();
        contentPanel.add(table);
        contentPanel.add(new Label("Empty"));
        initWidget(contentPanel);
    }

    EmptyProvider createEmptyProvider() {
        return new EmptyProvider() {
            @Override
            public void refresh() {
                getDetailModel().getForceRefreshCommand().Execute();
            }
        };
    }

    SimpleActionTable<HostInterfaceLineModel> createEmptyTable(EmptyProvider dataProvider) {
        Resources resources = GWT.<Resources> create(SubTableResources.class);
        return new SimpleActionTable<HostInterfaceLineModel>(dataProvider, resources);
    }

    void initTable() {
        table.addColumn(new EmptyColumn(), "", "10%");
        table.addColumn(new EmptyColumn(), "Name", "20%");
        table.addColumn(new EmptyColumn(), "Address", "20%");
        table.addColumn(new EmptyColumn(), "MAC", "20%");
        table.addColumnWithHtmlHeader(new EmptyColumn(), "Speed <sub>(Mbps)</sub>", "10%");
        table.addColumnWithHtmlHeader(new EmptyColumn(), "Rx <sub>(Mbps)</sub>", "10%");
        table.addColumnWithHtmlHeader(new EmptyColumn(), "Tx <sub>(Mbps)</sub>", "10%");
        table.addColumnWithHtmlHeader(new EmptyColumn(), "Drops <sub>(Pkts)</sub>", "10%");
        table.addColumn(new EmptyColumn(), "Bond", "20%");
        table.addColumn(new EmptyColumn(), "Vlan", "20%");
        table.addColumn(new EmptyColumn(), "Network Name", "20%");
        table.addActionButton(
                new UiCommandButtonDefinition<HostInterfaceLineModel>(getDetailModel().getEditCommand(), "Add / Edit"));
        table.addActionButton(
                new UiCommandButtonDefinition<HostInterfaceLineModel>(getDetailModel().getEditManagementNetworkCommand(),
                        "Edit Management Network"));
        // TODO: separator
        table.addActionButton(
                new UiCommandButtonDefinition<HostInterfaceLineModel>(getDetailModel().getBondCommand(), "Bond"));
        table.addActionButton(
                new UiCommandButtonDefinition<HostInterfaceLineModel>(getDetailModel().getDetachCommand(), "Detach"));
        // TODO: separator
        table.addActionButton(
                new UiCommandButtonDefinition<HostInterfaceLineModel>(getDetailModel().getSaveNetworkConfigCommand(),
                        "Save Network Configuration"));

        table.showRefreshButton();
    }

    @Override
    public void setMainTabSelectedItem(VDS selectedItem) {
        HostInterfaceForm hostInterfaceForm = new HostInterfaceForm(getDetailModel());
        contentPanel.remove(contentPanel.getWidgetCount() - 1);
        contentPanel.add(hostInterfaceForm);
    }

}
