package com.jointcorp.jcdata.vo;

/**
 * 页面 总运动数据
 */
public class TotalSportDataVO {

    private String[] steps;
    private String[] sportTimes;
    private String[] distances;
    private String[] calories;
    private String[] goals;
    private String[] strengths;

    public TotalSportDataVO() {
    }

    /**
     *
     * @param steps  步数
     * @param sportTimes  运动时长
     * @param distances 距离
     * @param calories 卡路里
     * @param goals 目标完成率
     * @param strengths  强度运动时长
     */
    public TotalSportDataVO(String[] steps, String[] sportTimes, String[] distances,
                            String[] calories, String[] goals, String[] strengths) {
        this.steps = steps;
        this.sportTimes = sportTimes;
        this.distances = distances;
        this.calories = calories;
        this.goals = goals;
        this.strengths = strengths;
    }

    public String[] getSteps() {
        return steps;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public String[] getSportTimes() {
        return sportTimes;
    }

    public void setSportTimes(String[] sportTimes) {
        this.sportTimes = sportTimes;
    }

    public String[] getDistances() {
        return distances;
    }

    public void setDistances(String[] distances) {
        this.distances = distances;
    }

    public String[] getCalories() {
        return calories;
    }

    public void setCalories(String[] calories) {
        this.calories = calories;
    }

    public String[] getGoals() {
        return goals;
    }

    public void setGoals(String[] goals) {
        this.goals = goals;
    }

    public String[] getStrengths() {
        return strengths;
    }

    public void setStrengths(String[] strengths) {
        this.strengths = strengths;
    }

    @Override
    public String toString() {
        return "TotalSportDataVO{" +
                "steps=" + steps +
                ", sportTimes=" + sportTimes +
                ", distances=" + distances +
                ", calories=" + calories +
                ", goals=" + goals +
                ", strengths=" + strengths +
                '}';
    }
}
