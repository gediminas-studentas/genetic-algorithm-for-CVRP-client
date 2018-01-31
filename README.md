# genetic-algorithm-for-CVRP-client
Parametrai
•	-h : serverio adresas;
•	-p : serverio prievadas;
•	-f : CVRP problemos failo vieta;
•	-rate : nustatoma, kokiu dažniu milisekundėmis klientas nori gauti atnaujinimus apie geriausią iki šio rezultatą;
•	-cycles : maksimalus iteracijų skaičius;
•	-init_size : maksimalus populiacijos dydis;
•	-elitism : elitismRate parametras nuo 0 iki 1;
•	-pmx_rate : kryžminimo tikimybė nuo 0 iki 1;
•	-swap_rate : sukeitimo mutacijos tikimybė nuo 0 iki 1;
•	-insert_rate : įdėjimo mutacijos tikimybė nuo 0 iki 1;
•	-shuffle_rate : išmaišymo mutacijos tikimybė nuo 0 iki 1;

## Example
java -jar bin/client.jar -f Instances\\A\\A-n45-k6.vrp -rate 10000 -init_size 800 -limit 1000 -pmx_rate 0.8 -swap_rate 0.1 -insert_rate 0.1 -shuffle_rate 0.05 -elitism 0.7 -p 8003
