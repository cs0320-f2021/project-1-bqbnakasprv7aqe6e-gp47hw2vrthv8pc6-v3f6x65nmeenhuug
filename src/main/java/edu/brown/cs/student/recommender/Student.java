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
	private Boolean prefersMarginalizedGroup; 
	private Set<String> marginalizedGroups;

	public Student(String id) {
		this.id = id;
	}

	public void addSkill(KVPair<String, Double> skill) {
		skills.add(skill);
	}

	public Boolean getPrefersMarginalizedGroup() {
		return prefersMarginalizedGroup;
	}

	public void setPrefersMarginalizedGroup(Boolean prefersMarginalizedGroup) {
		this.prefersMarginalizedGroup = prefersMarginalizedGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public List<String> getVectorRepresentation() {
		if (this.prefersMarginalizedGroup) {
			// Combine in a set to avoid duplicates
			Set<String> combinedTraits = new HashSet<String>();
			combinedTraits.addAll(this.traits);
			combinedTraits.addAll(this.marginalizedGroups);

			return new ArrayList<String>(combinedTraits);
		} else {
			return new ArrayList<String>(traits);
		}
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
