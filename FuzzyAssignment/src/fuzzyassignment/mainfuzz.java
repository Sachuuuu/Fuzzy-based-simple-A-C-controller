package fuzzyassignment;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.*;
import net.sourceforge.jFuzzyLogic.ruleConnectionMethod.RuleConnectionMethodAndMin;
import net.sourceforge.jFuzzyLogic.ruleConnectionMethod.RuleConnectionMethodOrMax;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;

/**
 *
 * Test parsing an FCL file updated
 *
 * @author pcingola@users.sourceforge.net
 */
public class mainfuzz {

    private double temp;
    private int humidity;
    private int crowd;

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setCrowd(int crowd) {
        this.crowd = crowd;
    }

    
    public mainfuzz(int hum, double temp, int crowd) {
        setHumidity(hum);
        setTemp(temp);
        setCrowd(crowd);
    }

    public void mainAc() throws Exception {

        String fileName = "C:\\Users\\Sathitha\\Documents\\NetBeansProjects\\FuzzyAssignment\\src\\fuzzyassignment\\airconditioner-fcl.fcl";
        FIS fis = FIS.load(fileName, true);

        // Error while loading?
        if (fis == null) {
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }

        FunctionBlock functionBlock = fis.getFunctionBlock("airconditioner");
        JFuzzyChart.get().chart(functionBlock);
        Variable tip = functionBlock.getVariable("CompressedSpeed");

        fis.setVariable("temperature", this.temp);
        fis.setVariable("humidity", this.humidity);
        fis.setVariable("crowd", this.crowd);

        fis.evaluate();

        JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);
        System.out.println("Defuzzified Value  " + tip.getDefuzzifier().defuzzify());

        for (Rule r : fis.getFunctionBlock("airconditioner").getFuzzyRuleBlock("rules").getRules()) {
            double value = Double.parseDouble(r.toString().split("\\s")[1].replace("(", "").replace(")", ""));
            if (value > 0.0){
                main.jTextArea1.append(r.toString()+"\n");
                System.out.println(r);
            }
        }

        System.out.println(fis);
    }
}
