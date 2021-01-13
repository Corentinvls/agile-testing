Feature: Fonctionnalités de ma page d'accueil
	Scenario: Vérification du titre et de la description
		Given je suis sur la homepage
		Then le titre doit être "Partagez vos passions | Meetup"
		And la description contient "Partagez vos passions et faites bouger votre ville"

	Scenario: Vérification de la punchline et sous-punchline du site
		Given je suis sur la homepage
		Then La punchline doit être "Le monde vous tend les bras"
		And La sous-punchline doit être "Rejoignez un groupe local pour rencontrer du monde, tester une nouvelle activité ou partager vos passions."

	Scenario: Vérification du bouton d'inscription rouge
		Given je suis sur la homepage
		Then Le bouton doit être "rgb(241, 58, 89) none repeat scroll 0% 0% / auto padding-box border-box"
		And Le bouton doit avoir ecrit "Rejoindre Meetup"
		And Le bouton doit contenir le lien "https://www.meetup.com/fr-FR/register/"