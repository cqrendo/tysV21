package coop.intergal.tys.ui.components.detailsdrawer;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.tabs.Tabs;

import coop.intergal.tys.ui.components.FlexBoxLayout;
import coop.intergal.tys.ui.layout.size.Horizontal;
import coop.intergal.tys.ui.layout.size.Right;
import coop.intergal.tys.ui.layout.size.Vertical;
import coop.intergal.tys.ui.util.BoxShadowBorders;
import coop.intergal.tys.ui.util.UIUtils;

public class DetailsDrawerHeader extends FlexBoxLayout {

    private Button close;
    private Label title;

    public DetailsDrawerHeader(String title) {
        addClassName(BoxShadowBorders.BOTTOM);
        setFlexDirection(FlexDirection.COLUMN);
        setWidthFull();

        this.close = UIUtils.createTertiaryInlineButton(VaadinIcon.CLOSE);
        UIUtils.setLineHeight("1", this.close);

        this.title = UIUtils.createH3Label("title");

        FlexBoxLayout wrapper = new FlexBoxLayout(this.close, this.title);
        wrapper.setAlignItems(FlexComponent.Alignment.CENTER);
        wrapper.setPadding(Horizontal.RESPONSIVE_L, Vertical.M);
        wrapper.setSpacing(Right.L);
        add(wrapper);
    }

    public DetailsDrawerHeader(String title, Tabs tabs) {
        this(title);
        add(tabs);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void addCloseListener(ComponentEventListener<ClickEvent<Button>> listener) {
        this.close.addClickListener(listener);
    }

}
