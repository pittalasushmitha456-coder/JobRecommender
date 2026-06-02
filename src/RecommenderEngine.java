import java.util.ArrayList;    //to store dynamic data skills
import java.util.Collections;  //to perform operations like sorting
import java.util.List;   //provides flexible way to store collections of data

public class RecommenderEngine {

    public static class Result {
        public double matchScore;   //percentage
        public List<String> matchedSkills = new ArrayList<>();  //skills user has
        public List<String> missingSkills = new ArrayList<>();  //skills user doesnt hava
        public List<String> recommendedPath = new ArrayList<>(); //what to learn
    }

    public static Result analyze(String resumeText, Job targetJob) { //takes i/p,job data  return result
        Result result = new Result();
        String cleanResume = resumeText.toLowerCase(); //convert to lowercase
        double totalWeight = 0;
        double matchedWeight = 0;

        class MissingSkill implements Comparable<MissingSkill> {
            String name;
            int weight;

            MissingSkill(String n, int w) { //used to sort missing skill with weight
                name = n;
                weight = w;
            }

            public int compareTo(MissingSkill o) {
                return o.weight - this.weight;
            }
        }

        List<MissingSkill> missingList = new ArrayList<>();

        for (int i = 0; i < targetJob.getSkills().size(); i++) { //iterates all required skills of selectec job
            String skill = targetJob.getSkills().get(i);
            int weight = targetJob.getWeights().get(i);

            totalWeight += weight;

            if (cleanResume.contains(skill.toLowerCase().trim())) {
                matchedWeight += weight;
                result.matchedSkills.add(skill);
            } else {
                missingList.add(new MissingSkill(skill, weight));
            }
        }

        result.matchScore = (matchedWeight / totalWeight) * 100;

        Collections.sort(missingList);

        for (MissingSkill ms : missingList) {
            result.missingSkills.add(ms.name);
            result.recommendedPath.add(ms.name + " (Priority: " + ms.weight + ")");
        }

        return result;
    }
}
