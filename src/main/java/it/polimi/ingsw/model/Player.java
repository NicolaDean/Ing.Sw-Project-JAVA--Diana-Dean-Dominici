package it.polimi.ingsw.model;

public class Player {

    private String Nickname;
    private boolean ConnectionState;
    private LeaderCard[] Leaders;
    private Dashboard Dashboard;
    private int Position;
    private int Score;

    public Player(String nickname,LeaderCard[] drawedCards)
    {
        this.Nickname = nickname;
    }

    public String getNickname()
    {
        return this.Nickname;
    }
    /**
     * increment player position by 1
     */
    public void incrementPosition()
    {
        this.Position++;
    }

    /**
     * true if Online
     * false if Offline
     * @return
     */
    public boolean checkConnection()
    {
        return this.ConnectionState;
    }

    public int getPosition() {
        return this.Position;
    }

    public Dashboard getDashboard() {
        return this.Dashboard;
    }

    /**
     * Cominica la fine del turno alla classe game
     */
    public void endTurn(){}

    /**
     * Calculate the score from the Production Cards, Player position and ...
     * @return
     */
    private int getDashboardScore()
    {
        return this.Dashboard.getScore();
    }

    /**
     * Calculate the score from the Leader Victory Points
     * @return
     */
    private int getCardsScore()
    {
        int vp=0;
        for(LeaderCard Leader : Leaders)
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
        Leaders[position] = null;
    }


}
