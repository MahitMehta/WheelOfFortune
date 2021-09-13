class Player {
    int playerId;
    int points = 0;

    public Player(int playerId, int pointsEarned) {
        this.playerId = playerId;
        this.points += pointsEarned;
    }
}