# Bonhomis-Adventures
Video game for ISN project.

$$Présentation générale :$$
Il s'agit d'un jeu vidéo en 2D vue de dessus où un personnage doit traverser des salles créées aléatoirement par génération procédurale afin d'atteindre la fin du niveau en ayant collecté des items clés, tout en combattant des ennemis.


$$Histoire du jeu et but:$$
Bonhomi, le héros, s'est fait voler son goûter et veux le récupérer des mains des tyrans du château Baston de l'autre coté de la rue. Il s'engouffre donc dans le château et son aventure y prend place. 
Il doit récupérer son goûter dans les salles : 
une orange
une compote de pomme
une brique de lait au chocolat
des petits gâteaux au chocolat


$$Programmation et mécaniques :$$
La plate-forme utilisée est Java avec l'IDE « Eclipse ». 

Le moteur de jeu comportera une boucle de jeu mettant à jour la logique et les graphisme à chaque trame.

La carte sera de 64 salles divisées. Ces salles seront générées de manière procédurale et positionnées aléatoirement dans le niveau. L'algorithme vérifiera qu'il existe bien un chemin entre la salle d'entrée et la salle de sortie. 

Parmi les 64 salles, 4 seront choisies au hasard pour abriter les aliments composant le goûter de Bonhomi. Il y aura des ennemis dans quasiment chaque salle.

Notre héros possède 5 points de vie représentés sous forme de cœurs. Il peut perdre de la vie en touchant un monstre et en récupère en collectant un des éléments du goûter. Lorsque Bonhomi n'a plus de vies, la partie est terminée.
