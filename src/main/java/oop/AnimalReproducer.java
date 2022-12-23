package oop;

import java.util.Random;
import java.util.Vector;

public class AnimalReproducer {

    private Random generator = new Random();
    private final Animal mother;
    private final Animal father;
    private final MutationType mutationType;//przenies to do zwierzęcia
    private final int maxMutationQuantity;
    private final int minMutationQuanity;

    public AnimalReproducer(Animal mother, Animal father,MutationType mutationType, int maxMutationQuantity,int minMutationQuanity){
        this.mother = mother;
        this.father = father;
        this.mutationType = mutationType;
        this.minMutationQuanity = minMutationQuanity;
        this.maxMutationQuantity = maxMutationQuantity;
    }

    public Animal createAnimal(){




    }



}
