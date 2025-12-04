import pokemon.*;
import ru.ifmo.se.pokemon.*;


public class Main{
 public static void main(String[] args){
     Battle battle = new Battle();

     Ariados ariados = new Ariados("AR",1);
     Bellsprout bellsprout = new Bellsprout("BL",1);
     Weepinbell weepinbell = new Weepinbell("Wp",1);
     Spinarak spinarak = new Spinarak("Sp",1);
     Furfrou furfrou = new Furfrou("Fu",1);
     Victreebel victreebel = new Victreebel("Vi",1);

     battle.addAlly(ariados);
     battle.addAlly(bellsprout);
     battle.addAlly(weepinbell);

     battle.addFoe(spinarak);
     battle.addFoe(furfrou);
     battle.addFoe(victreebel);
     battle.go();

 }

}











