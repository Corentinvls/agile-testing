Feature: Fonctionnalités de la fiche meetup

  Scenario: Vérification du Header
    Given je suis sur une page meetup
    Then le titre doit etre cliquable avec le nom du meetup
    And un lieu
    And le total des membres
    And les organisateurs
    And un bouton avec un label "Rejoindre ce groupe"
    And une photo de presentation.

  Scenario: Vérification du stripe
    Given je suis sur une page meetup
    Then La fiche doit contenir un bandeau d'onglets avec les bouttons suivant
      | Événements | Photos | Membres | Discussions | À propos | Plus |

  Scenario: Vérification des Buttons voir tout
    Given je suis sur une page meetup
    Then La page doit contenir les quatre liens "Voir tout"

  Scenario: Vérification du signalement evenement a venir
    Given je suis sur une page meetup avec un evenement a venir
    Then La page doit contenir une card pour le meetup a venir ou un signalement aucun evenement

  Scenario: Vérification pour rejoindre un groupe
    Given je suis sur une page meetup
    When je clicke sur le bouton Rejoindre
    Then je dois pouvoir m'identifier avec un lien "https://www.meetup.com/fr-FR/login/"
    And je dois pouvoir me connecter avec facebook ou google
    When je peux aussi clicke sur m'inscrire
    And etre rediriger vers "https://secure.meetup.com/fr-FR/register/"

  Scenario: Vérification contacter l'organisateur
    Given je suis sur une page meetup
    When je clicke sur le boutton contacter
    Then je dois etre rediriger sur "https://secure.meetup.com/fr-FR/login/"

