package game;

import framework.internacionalization.Internacionalization;
import java.text.MessageFormat;
import java.util.Random;

/**
 * 
 * @author Kuroda
 */
public class GameControl {

    public static int asteroidsToDodge = 5;
    public static int aliensToKill = 0;
    public static String stageObject = "Objetivo: desviar de 5 asteróides";
    public static Random random = new Random();
    private static int[] positions;

    /*  Exemplo: 2 vias
    Porcentagem de espaco: 100 / (2 + 1) = 33% para cada via
    posicao[0] == 800 * (33 * (0 + 1)) / 100 = 264
    posicao[1] == 800 * (33 * (1 + 1)) / 100 = 528
     */
    public static int randomizeXPosition() {
        int position;
        positions = new int[Global.vias];
        int percent = 100 / (Global.vias + 1);
        for (int i = 0; i < Global.vias; i++) {
            position = Global.SCREEN_WIDTH * (percent * (i + 1)) / 100;
            positions[i] = position;
        }
        // Retorna alguma via randomica
        return positions[random.nextInt(Global.vias)];
    }

    /*
     * Atualizar ao passar de estagio.
     */
    public static void update() {

        // Aumenta um estagio
        Global.stage++;

        // A cada x fases, aumenta uma 'via' para os inimigos
        /*if (Global.stage % Global.difficulty == 0) {
        Global.objectsSpeed += 0.25;
        Global.variationSpeed += 0.25;
        }*/

        Global.aliensKill = 0;
        Global.asteroidsDodge = 0;

        switch (Global.stage) {
            case 1: {
                asteroidsToDodge = 5;
                aliensToKill = 0;
                stageObject = MessageFormat.format(Internacionalization.get("Objetivo1"), asteroidsToDodge);
            }
            break;
            case 2: {
                Global.aliens++;
                asteroidsToDodge = 0;
                aliensToKill = 5;
                stageObject = MessageFormat.format(Internacionalization.get("Objetivo3"), aliensToKill);
            }
            break;
            case 3: {
                asteroidsToDodge = 5;
                aliensToKill = 5;
                Global.objectsSpeed += 0.5;
                Global.vias += 1;
                stageObject = MessageFormat.format(Internacionalization.get("Objetivo2"), asteroidsToDodge, aliensToKill);
            }
            break;
            case 4: {
                asteroidsToDodge = 10;
                aliensToKill = 0;
                stageObject = MessageFormat.format(Internacionalization.get("Objetivo1"), asteroidsToDodge);
            }
            break;
            case 5: {
                asteroidsToDodge = 0;
                aliensToKill = 10;
                Global.asteroids = 2;
                Global.vias += 1;
                stageObject = MessageFormat.format(Internacionalization.get("Objetivo1"), aliensToKill);
                Global.objectsSpeed += 0.5;
            }
            break;
            case 6: {
                asteroidsToDodge = 10;
                aliensToKill = 10;
                Global.asteroids = 1;
                Global.aliens = 2;
                stageObject = MessageFormat.format(Internacionalization.get("Objetivo2"), asteroidsToDodge, aliensToKill);
            }
            break;
            case 7: {
                asteroidsToDodge = 20;
                aliensToKill = 15;
                Global.asteroids = 2;
                Global.aliens = 2;
                Global.objectsSpeed += 1;
                Global.vias += 1;
                stageObject = MessageFormat.format(Internacionalization.get("Objetivo1"), asteroidsToDodge);
            }
            break;
            case 8: {
                asteroidsToDodge = 10;
                aliensToKill = 5;
                Global.asteroids = 2;
                Global.aliens = 2;
                stageObject = MessageFormat.format(Internacionalization.get("Objetivo2"), asteroidsToDodge, aliensToKill);
            }
            break;
            case 9: {
                asteroidsToDodge = 15;
                aliensToKill = 10;
                Global.asteroids = 2;
                Global.aliens = 2;
                stageObject = MessageFormat.format(Internacionalization.get("Objetivo2"), asteroidsToDodge, aliensToKill);
            }
            break;
            case 10: {
                asteroidsToDodge = 40;
                aliensToKill = 30;
                Global.asteroids = 3;
                Global.aliens = 2;
                Global.variationSpeed += 0.5;
                Global.vias += 1;
                stageObject = MessageFormat.format(Internacionalization.get("Objetivo1"), asteroidsToDodge);
            }
            break;
            /*    case 11: {
            asteroidsToDodge = 5;
            aliensToKill = 5;
            Global.asteroids = 1;
            Global.aliens = 0;
            stageObject = MessageFormat.format(Internacionalization.get("Objetivo2"), asteroidsToDodge, aliensToKill);
            }
            break;
            case 12: {
            asteroidsToDodge = 10;
            aliensToKill = 0;
            Global.asteroids = 1;
            Global.aliens = 3;
            stageObject = MessageFormat.format(Internacionalization.get("Objetivo1"), asteroidsToDodge);
            }
            break;
            case 13: {
            asteroidsToDodge = 0;
            aliensToKill = 10;
            Global.asteroids = 3;
            Global.aliens = 2;
            stageObject = MessageFormat.format(Internacionalization.get("Objetivo3"), aliensToKill);
            }
            break;
            case 14: {
            asteroidsToDodge = 5;
            aliensToKill = 10;
            Global.asteroids = 2;
            Global.aliens = 3;
            stageObject = MessageFormat.format(Internacionalization.get("Objetivo2"), asteroidsToDodge, aliensToKill);
            }
            break;
            case 15: {
            asteroidsToDodge = 15;
            aliensToKill = 15;
            Global.asteroids = 3;
            Global.aliens = 2;
            stageObject = MessageFormat.format(Internacionalization.get("Objetivo2"), asteroidsToDodge, aliensToKill);
            }
            break;
            case 16: {
            asteroidsToDodge = 5;
            aliensToKill = 0;
            Global.asteroids = 1;
            Global.aliens = 0;
            Global.objectsSpeed += 0.25;
            stageObject = MessageFormat.format(Internacionalization.get("Objetivo1"), asteroidsToDodge);
            }
            break;
            case 17: {
            asteroidsToDodge = 0;
            aliensToKill = 5;
            Global.asteroids = 2;
            Global.aliens = 1;
            stageObject = MessageFormat.format(Internacionalization.get("Objetivo3"), aliensToKill);
            }
            break;
            case 18: {
            asteroidsToDodge = 10;
            aliensToKill = 10;
            Global.asteroids = 3;
            Global.aliens = 2;
            stageObject = MessageFormat.format(Internacionalization.get("Objetivo2"), asteroidsToDodge, aliensToKill);
            }
            break;
            case 19: {
            asteroidsToDodge = 20;
            aliensToKill = 20;
            Global.asteroids = 3;
            Global.aliens = 3;
            Global.objectsSpeed += 0.1;
            stageObject = MessageFormat.format(Internacionalization.get("Objetivo2"), asteroidsToDodge, aliensToKill);
            }
            break;
            case 20: {
            asteroidsToDodge = 0;
            aliensToKill = 0;
            stageObject = MessageFormat.format(Internacionalization.get("ObjetivoFinal"), asteroidsToDodge);
            }
            break;*/
        }
    }

    public static void reset() {
        aliensToKill = 0;
        asteroidsToDodge = 5;

        stageObject = MessageFormat.format(Internacionalization.get("Objetivo1"), asteroidsToDodge);
    }
}
