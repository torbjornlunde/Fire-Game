import processing.core.*;

public class Burnable extends Material {

    Burnable(String filename) {
        loadMap(filename);
        flammable = true;
    }

    public boolean fireIteration() {
        PVector prevFirePos = new PVector(fire.pos.x, fire.pos.y);

        /* move up on burning material, burn down if at top
        if (level.positionIsFlammable(fire.pos.x, fire.pos.y -1))
             fire.pos.y--;
        //else fire.pos.y++; // */


        //if (level.positionIsFlammable(fire.pos.x, fire.pos.y -1)) fire.pos.y--;
        PVector flammablePos = level.flammablePositionWithinArea(fire.pos.x, fire.pos.y-fire.hitBox/2, fire.hitBox);
        if (flammablePos != null) {
            if (p.debug != null) p.debug.lastFoundPosition = flammablePos;
            fire.pos.y--;
        }
        //else fire.pos.y++; //*/

        if (gameInput.moveLeft()) moveFireX(-1);
        else if (gameInput.moveRight()) moveFireX(1);

        // burn between previous pos and current pos
        if (fire.pos.x != prevFirePos.x || fire.pos.y != prevFirePos.y) {
            materialMask.beginDraw();
            materialMask.stroke(0);
            materialMask.point(prevFirePos.x, prevFirePos.y);
            materialMask.endDraw();
        }

        return false;
    }

    void moveFireX(int xOffset) {
        int x = (int) fire.pos.x + xOffset;
        int yLimit = 15;

        // Check above
        for (int yOffset=0; yOffset>-yLimit; yOffset--) {
            int y = (int) fire.pos.y + yOffset;
            if (level.positionIsFlammable(x, y)) {
                fire.pos.x = x;
                fire.pos.y = y;
                return;
            }
        }

        // Check bellow
        for (int yOffset=0; yOffset<yLimit; yOffset++) {
            int y = (int) fire.pos.y + yOffset;
            if (level.positionIsFlammable(x, y)) {
                fire.pos.x = x;
                fire.pos.y = y;
                return;
            }
        }
    }

}
