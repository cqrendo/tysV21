package coop.intergal.tys.ui.views.maestros.personasentidades;

import java.util.ArrayList;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.AppConst;
import coop.intergal.ui.views.DynamicGridForPick;
import coop.intergal.ui.views.GenericDynamicForm;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;

/**
 * A Designer generated component for the proveedores-ficha template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("proveedores-ficha")
@JsModule("./src/views/Maestros/PersonasEntidades/proveedores-ficha.js")
public class ProveedoresFicha extends GenericDynamicForm implements BeforeEnterObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id("title")
	private H3 title;
	@Id("dialogForPick")
	
	private Dialog dialogForPick;
	private DynamicGridForPick dynamicGridForPick = new DynamicGridForPick(); 
	
	private DynamicDBean bean;
	private DdbDataBackEndProvider dataProvider;
	private ArrayList<String[]> rowsColList;

    /**
     * Creates a new ProveedoresFicha.
     */
    public ProveedoresFicha() {
		super();
		dialogForPick.setWidth(AppConst.DEFAULT_PICK_DIALOG_WITHD);
		dialogForPick.setHeight(AppConst.DEFAULT_PICK_DIALOG_HEIGHT);
    }
//	@Override
	public void setBinder(Binder<DynamicDBean> binder2) {
		super.binder = binder2;
		if (bean != null)
			binder.setBean(bean);
		bindFields(ProveedoresFicha.class, this);
		super.setDialogForPick(dialogForPick);
	}
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (bean != null)
			binder.setBean(bean);
	}

	public DynamicDBean getBean() {
		return bean;
	}

	public void setBean(DynamicDBean bean) {
		this.bean = bean;
	}
	public DdbDataBackEndProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(DdbDataBackEndProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

    /**
     * This model binds properties between ProveedoresFicha and proveedores-ficha
     */
    public interface ProveedoresFichaModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
