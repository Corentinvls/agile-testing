Feature: Fonctionnalités de la page évènements
	Scenario: Vérification de l'accès à la page évènement depuis le menu de la page d'accueil
		Given je suis sur la homepage
		When je clique sur le menu burger puis le plus puis événements
		Then je dois arriver sur le page "https://www.tesla.com/fr_FR/events"

	Scenario: Vérification des événements proche du lieu choisi
		Given je suis sur la page événements
		When je choisi un lieu dans le monde
		Then je dois avoir les prochains événements

	Scenario: Vérification des liens afficher tous et plus de resultats
		Given je suis sur la page événements
		Then je dois avoir les deux liens
			|Voir tous les événements|AFFICHER PLUS|

	Scenario: Je dois avoir un formulaire d'informations
		Given je suis sur la page événements
		Then le formulaire doit contenir
			|firstname_td|lastname_td|usermail_td|phonenumber_td|countries_td|zipcode_td|updates_opt_in_td|
		And un bouton d'envoi dont le texte est "Suivant"
		When j'envoi le formulaire sans remplir l'email
		Then un message rouge apparait en indiquant "obligatoire"

	Scenario: Vérification de la recherche d'événement
		Given je suis sur la page événements
		When je recherche "Londres, Royaume-Uni"
		Then le premier resultat doit être localisé à "Royaume-Uni"

	#J'ai du mettre Londres car aucun événement a venir sur Tokyo
	Scenario: Vérification de l'inscription à un événement
		Given je suis sur la page événements
		When je recherche et clique sur un événement "Londres"
		Then je suis redirigé vers la page "https://auth.tesla.com/"
