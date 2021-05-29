package coop.intergal.tys.ui.views.comprasyventas.articulos;

import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.AppConst;
import coop.intergal.ui.views.GenericDynamicForm;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.dialog.Dialog;

import coop.intergal.ui.components.EsDatePicker;
import coop.intergal.ui.utils.TranslateResource;
import coop.intergal.ui.views.DynamicViewGrid;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.AxisTitle;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataLabels;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.charts.model.PlotOptionsBar;
import com.vaadin.flow.component.charts.model.PlotOptionsLine;
import com.vaadin.flow.component.charts.model.VerticalAlign;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.charts.model.style.SolidColor;


/**
 * A Designer generated component for the articulo-form template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("histograma-form")
@JsModule("./src/views/ComprasYVentas/Articulos/histograma-form.js")
@Route(value = "histogramaArticulo")
public class HistogramaForm extends GenericDynamicForm implements BeforeEnterObserver{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id("col0")
	private TextField col0;
	@Id("col1")
	private TextField col1;
	@Id("col2")
	private TextField col2;
	@Id("col3")
	private TextField col3;
	@Id("col4")
	private TextField col4;
	@Id("col5")
	private TextField col5;
	@Id("col6")
	private TextField col6;
	@Id("col7")
	private TextField col7;
	@Id("col8")
	private TextField col8;
	@Id("col9")
	private TextField col9;
	@Id("col10")
	private TextField col10;
	@Id("col11")
	private TextField col11;
	@Id("col12")
	private TextField col12;
	@Id("col13")
	private TextField col13;
	@Id("col14")
	private TextField col14;
	@Id("col15")
	private TextField col15;
	private DdbDataBackEndProvider dataProvider;
	private DynamicDBean bean;
	@Id("dgSituacion")
	private DynamicViewGrid dgSituacion;
	@Id("dgProvLin")
	private DynamicViewGrid dgProvLin;
	@Id("dgProvAlt")
	private DynamicViewGrid dgProvAlt;
	@Id("char1")
	private Div char1;
	@Id("char2")
	private Div char2;
	@Id("char3")
	private Div char3;
	@Id("char4")
	private Div char4;
	
	/**
     * Creates a new HistogramaForm.
     */
//	   @EventHandler
//	    private void handleClickTabEvol() {
//	        System.out.println("Click Tab Evol");
//			dgEvol.setResourceName("CR-ARTICULOS.List-CONTROLSTOCKDIARIO-MJ");
//			dgEvol.setFilter("CLAVEARTICULO="+bean.getCol0());
//			dgEvol.getElement().getStyle().set("display", "block");
//			dgEvol.setButtonsRowVisible(false);
//			dgEvol.getGrid().setHeightByRows(true);
//			dgEvol.setupGrid(true,true);
//	    }

    public HistogramaForm() {
		super();
		montaChar("CR-ARTICULOS__Histograma.Grid-ARTICULO_SITUACION.List-ARTISITUMENSUAL","SALIDAS","ENTRADAS", char1, ChartType.BAR);
		montaChar("CR-ARTICULOS__Histograma.Grid-ARTICULO_SITUACION.List-ARTISITUGLOBAL__MJ","TOTALSALIDAS","TOTALENTRADAS", char2, ChartType.AREASPLINE);
		montaChar("CR-ARTICULOS__Histograma.Grid-ARTICULO_SITUACION.List-ARTISITUGALICIA__MJ","TOTALSALIDAS","TOTALENTRADAS", char3, ChartType.AREA);
		montaChar("CR-ARTICULOS__Histograma.Grid-ARTICULO_SITUACION.List-ARTISITUTODAGALICIA__MJ","TOTALSALIDAS","TOTALENTRADAS", char4, ChartType.BAR);
    }

	/**
     * This model binds properties between ArticuloForm and articulo-form
     */
    public interface ArticuloFormModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (bean != null)
		{
			setBean(bean);
		}

		
	}
	public void setBinder(Binder<DynamicDBean> binder2) {
		super.binder = binder2;
		bindFields(HistogramaForm.class, this);
	}
	public DdbDataBackEndProvider getDataProvider() {
		return dataProvider;
	}
	public void setDataProvider(DdbDataBackEndProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public void setBean(DynamicDBean bean) {
		System.out.println("HistogramaForm.setBean()");
		this.bean = bean;
		if (bean != null)
		{
			binder.setBean(bean);
			dgProvAlt.setResourceName("CR-ARTICULOS__Histograma.Grid-ARTICULOSCONPROVALTERNATIVO");
			dgProvAlt.setFilter("CLAVEARTICULO="+bean.getCol16());
			dgProvAlt.setButtonsRowVisible(false);
			dgProvAlt.getGrid().setHeightByRows(true);
			dgProvAlt.setupGrid();

			dgProvLin.setResourceName("CR-ARTICULOS__Histograma.Grid-OFERTASPROVLIN");
			dgProvLin.setFilter("CLAVEARTICULO="+bean.getCol16());
			dgProvLin.setButtonsRowVisible(false);
			dgProvLin.getGrid().setHeightByRows(true);
			dgProvLin.setupGrid();

			dgSituacion.setResourceName("CR-ARTICULOS__Histograma.Grid-ARTICULO_SITUACION");
			dgSituacion.setFilter("CLAVE_ARTICULO="+bean.getCol16());
			dgSituacion.setButtonsRowVisible(false);
			dgSituacion.getGrid().setHeightByRows(true);
			dgSituacion.setupGrid();
		}
	}

    private void montaChar(String recurso, String salen, String entran, Div elDiv, ChartType bar) {
    	Chart chart = new Chart(bar);
        Configuration configuration = chart.getConfiguration();
//        chart.getConfiguration().getChart().setType(bar);
 
        ListSeries salidas = new ListSeries("Salidas");
        ListSeries entradas = new ListSeries("Entradas");
        
//        PlotOptionsLine entradasOptionsLine = new PlotOptionsLine();
//        entradasOptionsLine.setColor(new SolidColor("#d62031"));
//        entradas.setPlotOptions(entradasOptionsLine);
        
        XAxis x = new XAxis();
        
    	ArrayList<String[]> rowsColList = new ArrayList<String[]>(); 	
    	String[] fieldArr = new String[6];
    	fieldArr[0] = "ANOMES"; //el id
    	fieldArr[1] = "";
    	fieldArr[2] = "";
    	fieldArr[3] = "";
    	fieldArr[4] = "";
    	fieldArr[5] = "";
		rowsColList.add(fieldArr);
		
    	fieldArr = new String[6];
    	fieldArr[0] = salen;  // el display
    	fieldArr[1] = "";
    	fieldArr[2] = "";
    	fieldArr[3] = "";
    	fieldArr[4] = "";
    	fieldArr[5] = "";
		rowsColList.add(fieldArr);
		
    	fieldArr = new String[6];
    	fieldArr[0] = entran;  // el display
    	fieldArr[1] = "";
    	fieldArr[2] = "";
    	fieldArr[3] = "";
    	fieldArr[4] = "";
    	fieldArr[5] = "";
		rowsColList.add(fieldArr);
        
        String filter = "CLAVEARTICULO=3146";//+bean.getCol16();
		Collection<DynamicDBean> collect = RestData.getResourceData(0, 300, recurso, "GFER21TYSLac0", rowsColList, filter, false, false, null);   
//		Collection<DynamicDBean> collect = RestData.getResourceData(0, 300, "CR-ARTICULOS__Histograma.Grid-ARTICULO_SITUACION.List-ARTISITUMENSUAL", AppConst.PRE_CONF_PARAM, rowsColList, filter, false, false, null);   
        Iterator<DynamicDBean> itAnos = collect.iterator();
        while (itAnos.hasNext()){
        	DynamicDBean beanEntradas = itAnos.next();
        	String ano = beanEntradas.getCol0();
        	double entrada = Double.parseDouble(beanEntradas.getCol1());
        	double salida = Double.parseDouble(beanEntradas.getCol2());
        	
        	x.addCategory(ano); 
        	salidas.addData(salida);
        	entradas.addData(entrada);
        }
        configuration.addSeries(salidas);
        configuration.addSeries(entradas);
        configuration.addxAxis(x);

        YAxis y = new YAxis();
        y.setMin(0);
        AxisTitle yTitle = new AxisTitle();
        yTitle.setText("Unidades");
        yTitle.setAlign(VerticalAlign.HIGH);
        y.setTitle(yTitle);
        configuration.addyAxis(y);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix("");
        configuration.setTooltip(tooltip);

        PlotOptionsBar plotOptions = new PlotOptionsBar();
        DataLabels dataLabels = new DataLabels();
        dataLabels.setEnabled(true);
        plotOptions.setDataLabels(dataLabels);
        configuration.setPlotOptions(plotOptions);

        elDiv.add(chart);

	}
	
	
	
}

