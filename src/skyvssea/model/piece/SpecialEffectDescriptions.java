package skyvssea.model.piece;

import java.util.ArrayList;
import java.util.List;

public class SpecialEffectDescriptions {
    private final  String[] SpecialEffectSharkDescription = {"Itself attack range double","Increasing own attack level from Medium to Big ","Increasing own attack level from Smallto Middle "};
    private final  String[] SpecialEffectEagleDescription = {"Move twice in one round","One piece cannot move itself and attack others","The attack and move range of one piece becomes half"};

    public SpecialEffectDescriptions(){

    };

    public String getSpecialEffectSharkDescription(int number) {
        return SpecialEffectSharkDescription[number];
    }

    public  String getSpecialEffectEagleDescription(int number) {
        return SpecialEffectEagleDescription[number];
    }
}