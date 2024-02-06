package org.slavawins.reassets.contracts.vanila;

import java.util.ArrayList;
import java.util.List;

public class VanilaSoundContract {

    public List<String> sounds = new ArrayList<>();

    public VanilaSoundContract(){}
    public VanilaSoundContract(String sound){
        sounds.add(sound);
    }
}
