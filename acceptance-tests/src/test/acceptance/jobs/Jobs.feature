Feature: Fonctionnalités de ma page Jobs
	Scenario: Vérification du titre et du bouton
		Given je suis sur "https://www.meetup.com/fr-FR/careers/"
		Then le titre pour job doit être "Join our team, find your people"
		And la button contient "Explore Opportunities"

	Scenario: Vérification du click sur Explore Opportunities
		Given je suis sur "https://www.meetup.com/fr-FR/careers/"
		When je clicke sur le bouton Explore Opportunities
		Then Le nombre d'emploies affiché est de "9"
		And le bouton all opportunities redirige vers "https://www.meetup.com/careers/all-opportunities"

	Scenario: Vérification du block carière chez MeetUp
		Given je suis sur "https://www.meetup.com/fr-FR/careers/"
		Then la punchline est "Perks and benefits"
		And Le nombre d'illustration affiché est de "6"
