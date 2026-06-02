import java.util.ArrayList; //to store dynamic data skills
import java.util.List;  //provides flexible way to store collections of data

public class Job {
    private String title;
    private List<String> skills;
    private List<Integer> weights;

    public Job(String title) {
        this.title = title;
        this.skills = new ArrayList<>();
        this.weights = new ArrayList<>();
    }

    public void addSkill(String skill, int weight) { //ex: ("java",5)
        this.skills.add(skill);
        this.weights.add(weight);
    }

    public String getTitle() {
        return title;
    } //to retrieve private data

    public List<String> getSkills() {
        return skills;
    }

    public List<Integer> getWeights() {
        return weights;
    }
    public int getWeightForSkill(String skill) {
        for (int i = 0; i < skills.size(); i++) { //iterates job skills and compares with resume
            if (skills.get(i).equalsIgnoreCase(skill)) {
                return weights.get(i);
            }
        }
        return 0;
    }
}
