Feature: Fonctionnalités de ma page de recherche
	Scenario: Vérification du titre de l'onglet et du H1
		Given je suis sur la recherche
		Then le titre contient "Nature et aventure"
		And le h1 contient "Nature et aventure"

	Scenario: Vérification du bandeau de recherche
		Given je suis sur la recherche
		Then le champs de recherche est
		And le rayon est
		And la ville est
		And le choix d'affichage est

	Scenario: Vérification de la liste de tri
		Given je suis sur la recherche
		Then le tri par défaut est "pertinence"
		And les tris possibles sont
			| pertinence | plus récents | nombre de membres | proximité |

	Scenario: Vérification du clique sur le choix calendrier
		Given je suis sur la recherche
		When je clique sur le bouton
		Then affiche les évènements et le calendrier de la "list"

	Scenario: Vérification du clique sur le 21 du mois
		Given je suis sur la recherche
		When je clique sur le /21 du mois courant
		Then affiche les évènements du /21
		When je clique sur un évènement de la liste
		Then je suis redirigé vers la page de l'évènement
