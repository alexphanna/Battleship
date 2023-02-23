public class Ship {
    public class Destroyer extends AbstractShip {
        public Destroyer() {
            super(2);
        }
    }
    class Submarine extends AbstractShip {
        public Submarine() {
            super(3);
        }
    }
    class Cruiser extends AbstractShip {
        public Cruiser() {
            super(3);
        }
    }
    class Battleship extends AbstractShip {
        public Battleship() {
            super(4);
        }
    }
    class Carrier extends AbstractShip {
        public Carrier() {
            super(5);
        }
    }
}