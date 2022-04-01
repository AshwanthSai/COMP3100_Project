public class Server implements Comparable<Server>{
    int serverID;
    int core;
    int memory;
    int disk;

    public Server(int serverID, int core, int memory, int disk){
        this.serverID = serverID;
        this.core = core;
        this.memory = memory;
        this.disk = disk;
    }

    @Override
    public int compareTo(Server s2) {
        return Comparators.core.compare(this, s2);
    }
    
}

