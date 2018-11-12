package applicationPackage.bins;

import applicationPackage.Repositories.VisitRepository;
import applicationPackage.entitys.Customer;
import applicationPackage.entitys.Visit;
import applicationPackage.utils.DateFormater;
import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.*;

@Named
@ViewScoped
public class ChartsPage {
    private ChartModel chartModel = new ChartModel();
    private BarChartModel barModel = new BarChartModel();
    private LineChartModel lineModel = new LineChartModel();
    private List selectedParemeters = new ArrayList();
    private String selectedPeriod = new String();
    private List<ChartSeries> listChartSeries;


    List<Visit> allVisits = new ArrayList<>();
    List<Visit> filteredVisits = new ArrayList<>();

    Long maxParam1 = new Long(0);
    Long maxParam2 = new Long(0);

    private String type = "bar";

    public List<ChartSeries> makeCharts() {

        listChartSeries = new ArrayList<>();
        Date fromDate = findRequestedDate(selectedPeriod);

        allVisits = visitRepository.findAll();
        filteredVisits = new ArrayList<>();

        for (Visit visit : allVisits) {
            if ((visit.getStart().after(fromDate) && !visit.getStart().after(new Date())) || sameDay(visit.getStart(), fromDate)) {
                filteredVisits.add(visit);
            }
        }
        ChartSeries resultChart;
        for (Object parametr : selectedParemeters) {
            Set<Date> setDates;
            if (String.valueOf(parametr).equals("Income")) {
                setDates = new TreeSet<>();
                resultChart = new ChartSeries();
                for (Visit visit : filteredVisits) {
                    setDates.add(visit.getStart());
                }
                for (Date date : setDates) {
                    Long sum = new Long(0);
                    for (Visit visit : filteredVisits) {
                        if (sameDay(date, visit.getStart()))
                            sum += visit.getFanalPrice();
                    }

                    if (sum / 100 > maxParam1) maxParam1 = sum / 100;
                    //                    if (sum > 0)
                    resultChart.set(new SimpleDateFormat("dd-MM").format(date), sum / 100);

                }
                resultChart.setLabel("Income");
                listChartSeries.add(resultChart);
            } else if (String.valueOf(parametr).equals("Clients")) {
                setDates = new HashSet<>();
                resultChart = new ChartSeries();
                for (Visit visit : filteredVisits) {
                    setDates.add(visit.getStart());
                }
                for (Date date : setDates) {
                    Long sum = new Long(0);
                    Set<Customer> uniqueClientinDay = new HashSet<>();
                    for (Visit visit : filteredVisits) {
                        if (sameDay(date, visit.getStart()) && !uniqueClientinDay.contains(visit.getCustomer()))
                            sum += 1;
                        uniqueClientinDay.add(visit.getCustomer());
                    }

                    if (sum > maxParam2) maxParam2 = sum;

                    resultChart.set(new SimpleDateFormat("dd-MM").format(date), sum);
                }
                resultChart.setLabel("Clients");
                listChartSeries.add(resultChart);
            }
        }
        return listChartSeries;
    }

    public Date cleanTime(Date dateForClean) {
        Calendar result = Calendar.getInstance();
        result.setTime(dateForClean);
        result.clear(Calendar.HOUR_OF_DAY);
        result.clear(Calendar.MINUTE);
        result.clear(Calendar.SECOND);
        result.clear(Calendar.MILLISECOND);
        return result.getTime();
    }

    //overload
    public Calendar cleanTime(Calendar calendarForClean) {
        calendarForClean.clear(Calendar.HOUR_OF_DAY);
        calendarForClean.clear(Calendar.MINUTE);
        calendarForClean.clear(Calendar.SECOND);
        calendarForClean.clear(Calendar.MILLISECOND);
        return calendarForClean;
    }


    public Calendar cleanTime() {
        Calendar result = Calendar.getInstance();
        result.setTime(new Date());
        result.clear(Calendar.HOUR_OF_DAY);
        result.clear(Calendar.MINUTE);
        result.clear(Calendar.SECOND);
        result.clear(Calendar.MILLISECOND);
        return result;
    }

    public Date findRequestedDate(String request) {
        Calendar calendar = cleanTime();
        switch (request) {
            case "day":
                return calendar.getTime();
            case "current week":
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Monday
                return calendar.getTime();
            case "7 days":
                int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
                calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear - 1);
                return calendar.getTime();
            case "current month":
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                return calendar.getTime();
            case "30 days":
                int monthOfYer = calendar.get(Calendar.MONTH);
                calendar.set(Calendar.MONTH, monthOfYer - 1);
                return calendar.getTime();
            case "year":
                calendar.set(Calendar.DAY_OF_YEAR, 1);
                return calendar.getTime();
            case "one year":
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
                return calendar.getTime();
            case "half year":
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 6);
                return calendar.getTime();
            default:
                return new Date();
        }
    }

    public boolean sameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR))
            return true;
        return false;
    }

    @Inject
    VisitRepository visitRepository;


    @PostConstruct
    public void init() {
        createModel();
        listChartSeries.clear();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        listChartSeries = makeCharts();


//        ChartSeries boys = new ChartSeries();
//        boys.setLabel("Boys");
//        boys.set("2004", 120);
//
//        ChartSeries girls = new ChartSeries();
//        girls.setLabel("Girls");
//        girls.set("2004", 52);
//
//        model.addSeries(boys);
//        model.addSeries(girls);
        for (ChartSeries chart : listChartSeries) {
            model.addSeries(chart);
        }
        return model;
    }

    private LineChartModel initLineModel() {
        LineChartModel modelLine = new LineChartModel();
        listChartSeries = makeCharts();
        for (ChartSeries chart : listChartSeries) {
            modelLine.addSeries(chart);
        }
        return modelLine;
    }

    public void setAxis() {
        Axis xAxis;
        Axis yAxis;
        if (type.equals("bar")) {
            xAxis = barModel.getAxis(AxisType.X);
            yAxis = barModel.getAxis(AxisType.Y);
        } else if (type.equals("line")) {
            lineModel.setShowPointLabels(true);
            xAxis = lineModel.getAxes().put(AxisType.X, new CategoryAxis("Years"));
            yAxis = lineModel.getAxis(AxisType.Y);
        } else {
            xAxis = barModel.getAxis(AxisType.X);
            yAxis = barModel.getAxis(AxisType.Y);
        }
        xAxis.setLabel("Dates");
        yAxis.setMin(0);
        yAxis.setMax(maxParam1 * 1.2);
    }

    private void createModel() {
        if (type.equals("bar")) {
            chartModel = barModel;
            chartModel = initBarModel();
        } else if (type.equals("line")) {
            chartModel = lineModel;
            chartModel = initLineModel();
        }
        chartModel.setTitle("Your Chart");
        chartModel.setLegendPosition("ne");
        setAxis();
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public List getSelectedParemeters() {
        return selectedParemeters;
    }

    public void setSelectedParemeters(List selectedParemeters) {
        this.selectedParemeters = selectedParemeters;
    }

    public String getSelectedPeriod() {
        return selectedPeriod;
    }

    public void setSelectedPeriod(String selectedPeriod) {
        this.selectedPeriod = selectedPeriod;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public ChartModel getChartModel() {
        return chartModel;
    }

    public void setChartModel(ChartModel chartModel) {
        this.chartModel = chartModel;
    }
}
