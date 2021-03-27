package it.polimi.ingsw.model;


public class Player {

    private String nickname;
    private boolean connectionState;
    private LeaderCard[] leaders;
    private Dashboard dashboard;
    private int position;
    private int score;

    public Player(String nickname,LeaderCard[] drawedCards)
    {
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return this.nickname;
    }
    /**
     * increment player position by 1
     */
    public void incrementPosition()
    {
        this.position++;
    }

    /**
     * true if Online
     * false if Offline
     * @return
     */
    public boolean checkConnection()
    {
        return this.connectionState;
    }

    public int getPosition() {
        return this.position;
    }

    public Dashboard getDashboard() {
        return this.dashboard;
    }

    /**
     * Cominica la fine del turno alla classe game
     */
    public void endTurn(){
        //Notify Game with event
    }

    /**
     * Calculate the score from the Production Cards, Player position and ...
     * @return
     */
    private int getDashboardScore()
    {
        return this.dashboard.getScore();
    }

    /**
     * Calculate the score from the Leader Victory Points
     * @return
     */
    private int getCardsScore()
    {
        int vp=0;
        for(LeaderCard Leader : leaders)
        {
            vp+=Leader.getScore();
        }
        return vp;
    }

    /**
     * Return the total score of the player by summing score from the map and cards
     * @return
     */
    public int getPlayerScore()
    {
        return this.getCardsScore() + getCardsScore();
    }

    /**
     * Delete Leader from the Leader List of the player
     * @param position
     */
    public void DiscardLeader(int position)
    {
        leaders[position] = null;
    }


}
