package edu.brown.cs.student.recommender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.brown.cs.student.ds.KVPair;

public class Student implements Item {
	private String id; 
	private String name;
	private Set<String> traits; 
	private List<KVPair<String, Double>> skills; 

	public Student(String id, Set<String> traits, List<KVPair<String, Double>> skills) {
		this.id = id; 
		this.traits = traits; 
		this.skills = skills; 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Empty constructor
	 */
	public Student(String id) {

	}

	public String getId() {
		return this.id;
	}

	public List<String> getVectorRepresentation() {
		return new ArrayList<String>(traits);
	}

	public void addTrait(String trait) {
		traits.add(trait);
	}

	public Set<String> getTraits() {
		return this.traits;
	}

	public void setSkills(List<KVPair<String, Double>> skills) {
		this.skills = skills;
	}

	public double[] getSkillsArray() {
		double[] skillsArray = new double[]{this.skills.size()};
		int i = 0;
		for (KVPair<String, Double> skill : this.skills) {
			skillsArray[i] = skill.getValue();
			i++;
		}
		return skillsArray;
	}
}
