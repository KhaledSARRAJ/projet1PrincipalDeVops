package fr.eql.projet01.init;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fr.eql.projet01.dao.AbonnementRepository;
import fr.eql.projet01.dao.AnnonceRepository;
import fr.eql.projet01.dao.DroitsRepository;
import fr.eql.projet01.dao.MotifResiliationRepository;
import fr.eql.projet01.dao.PublicationRepository;
import fr.eql.projet01.dao.SexeRepository;
import fr.eql.projet01.dao.SupportRepository;
import fr.eql.projet01.dao.ThemeRepository;
import fr.eql.projet01.dao.UtilisateurRepository;
import fr.eql.projet01.dao.VilleRepository;
import fr.eql.projet01.entity.Abonnement;
import fr.eql.projet01.entity.Annonce;
import fr.eql.projet01.entity.Droits;
import fr.eql.projet01.entity.MotifResiliation;
import fr.eql.projet01.entity.Publication;
import fr.eql.projet01.entity.Sexe;
import fr.eql.projet01.entity.Support;
import fr.eql.projet01.entity.Theme;
import fr.eql.projet01.entity.Utilisateur;
import fr.eql.projet01.entity.Ville;

@Profile("initData")
@Component
@Transactional
public class InitDataSet {

	@Autowired
	private AbonnementRepository abonnementRepository;

	@Autowired
	private AnnonceRepository annonceRepository;

	@Autowired
	private DroitsRepository droitsRepository;

	@Autowired
	private MotifResiliationRepository motifResiliationRepository;

	@Autowired
	private PublicationRepository publicationRepository;

	@Autowired
	private SexeRepository sexeRepository;

	@Autowired
	private SupportRepository supportRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ThemeRepository themeRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private VilleRepository villeRepository;

	@PostConstruct
	public void initJeuxDeDonnees() throws ParseException {
		// mettre les données
		Sexe SexeHomme = insertSexe(null, "Homme");
		Sexe sexeFemme = insertSexe(null, "Femme");
		Sexe sexeIndetermine = insertSexe(null, "Indéterminé");

		Droits droitsAdmin = insertDroits(null, "Administrateur");
		Droits droitsUtilisateur = insertDroits(null, "Utilisateur");
		Droits droitsPremium = insertDroits(null, "Utilisateur Premium");

		Ville Bordeaux = insertVille(null, "33000", "Bordeaux", "France");
		Ville Pantin = insertVille(null, "93500", "Pantin", "France");
		Ville Versailles = insertVille(null, "78000", "Versailles", "France");
		Ville Nanterre = insertVille(null, "92000", "Nanterre", "France");
		Ville VilleJuif = insertVille(null, "94800", "VilleJuif", "France");
		Ville Périgueux = insertVille(null, "24010", "Périgueux", "France");
		Ville Paris1 = insertVille(null, "75001", "Paris", "France");
		Ville Paris2 = insertVille(null, "75002", "Paris", "France");
		Ville Paris3 = insertVille(null, "75003", "Paris", "France");
		Ville Paris4 = insertVille(null, "75004", "Paris", "France");
		Ville Paris5 = insertVille(null, "75005", "Paris", "France");
		Ville Paris6 = insertVille(null, "75006", "Paris", "France");
		Ville Paris7 = insertVille(null, "75007", "Paris", "France");
		Ville Paris8 = insertVille(null, "75008", "Paris", "France");
		Ville Paris9 = insertVille(null, "75009", "Paris", "France");
		Ville Paris10 = insertVille(null, "75010", "Paris", "France");
		Ville Paris11 = insertVille(null, "75011", "Paris", "France");
		Ville Paris12 = insertVille(null, "75012", "Paris", "France");
		Ville Paris13 = insertVille(null, "75013", "Paris", "France");
		Ville Paris14 = insertVille(null, "75014", "Paris", "France");
		Ville Paris15 = insertVille(null, "75015", "Paris", "France");
		Ville Paris16 = insertVille(null, "75016", "Paris", "France");
		Ville Paris17 = insertVille(null, "75017", "Paris", "France");
		Ville Paris18 = insertVille(null, "75018", "Paris", "France");
		Ville Paris19 = insertVille(null, "75019", "Paris", "France");
		Ville Paris20 = insertVille(null, "75020", "Paris", "France");
		Ville Rennes = insertVille(null, "35200", "Rennes", "France");
		Ville Montpellier = insertVille(null, "34070", "Montpellier", "France");
		Ville Marseille = insertVille(null, "13001", "Marseille", "France");
		Ville Tours = insertVille(null, "37000", "Tours", "France");
		Ville Strasbourg = insertVille(null, "67100", "Strasbourg", "France");
		Ville Lille = insertVille(null, "59160", "Lille", "France");
		Ville Lyon = insertVille(null, "69001", "Lyon", "France");

		MotifResiliation motifResiliationTemps = insertMotifResiliation(null, "Manque de temps");
		MotifResiliation motifResiliationMAJ = insertMotifResiliation(null, "Je n'aime pas les dernières mises à jour");
		MotifResiliation motifResiliationInteret = insertMotifResiliation(null, "Ca ne m'intéresse plus");
		MotifResiliation motifResiliationAutre = insertMotifResiliation(null, "Autre");

		// Utilisateurs

		Utilisateur utilisateur1 = insertUtilisateur(null, "Bruno", "Berthiaume", "1970-12-14", SexeHomme,
				"bruno.berthiaume@mail.com", "0699085253", "57 rue de Raymond Poincaré", null, "bruno",
				passwordEncoder.encode("bruno"), "2018-02-15", null, Paris3, null, droitsUtilisateur);
		Utilisateur utilisateur2 = insertUtilisateur(null, "Lola", "Vercammen", "1980-10-14", sexeFemme,
				"lola.vercammen@mail.com", "0603010004", "10 Rue Edmond Rousse", null, "lola", passwordEncoder.encode("lola"), "2018-02-15", null,
				Paris4, null, droitsAdmin);

		Utilisateur utilisateur3 = insertUtilisateur(null, "Pénélope", "Descamps", "1960-12-14", sexeFemme,
				"penelope.descamps@mail.com", "0603010006", "75 Boulevard Arago", null, "penelope",
				passwordEncoder.encode("penelope"), "2018-02-15", null, Marseille, null, droitsUtilisateur);
		Utilisateur utilisateur4 = insertUtilisateur(null, "Lucas", "Renard", "1985-01-15", SexeHomme,
				"lucas.renard@mail.com", "0602149845", "15 Allées de l'Europe", null, "lucas",
				passwordEncoder.encode("lucas"), "2018-02-15", null, Bordeaux, null, droitsUtilisateur);
		Utilisateur utilisateur5 = insertUtilisateur(null, "Corinne", "Marques", "1984-02-14", sexeFemme,
				"corinne.marques@mail.com", "0602149848", "13 Rue de la Courneuve", null, "corinne",
				passwordEncoder.encode("corinne"), "2018-02-15", null, Paris1, null, droitsUtilisateur);
		Utilisateur utilisateur6 = insertUtilisateur(null, "Ralph", "Montjoie", "1975-08-10", SexeHomme,
				"ralph.montjoie@mail.com", "0602149852", "29 Rue de Champagne", null, "ralph",
				passwordEncoder.encode("ralph"), "2018-02-15", null, Paris3, null, droitsUtilisateur);
		Utilisateur utilisateur7 = insertUtilisateur(null, "Danielle", "Langlois", "1990-03-03", sexeFemme,
				"danielle.langlois@mail.com", "0602149854", "Rue de l'Asile Popincourt", null, "danielle",
				passwordEncoder.encode("danielle"), "2018-02-15", null, Paris17, null, droitsUtilisateur);
		Utilisateur utilisateur8 = insertUtilisateur(null, "Claire", "Poissonnier", "1998-05-05", sexeFemme,
				"claire.poissonnier@mail.com", "0603010040", "6 rue la Fayette", null, "claire",
				passwordEncoder.encode("claire"), "2018-02-15", null, Paris12, null, droitsUtilisateur);
		Utilisateur utilisateur9 = insertUtilisateur(null, "Charles", "Godin", "1994-06-08", SexeHomme,
				"charles.godin@mail.com", "0603010044", "29 rue Pierre Nicole", null, "charles",
				passwordEncoder.encode("charles"), "2018-02-15", null, Rennes, null, droitsUtilisateur);
		Utilisateur utilisateur10 = insertUtilisateur(null, "Annie", "Bonnet", "1970-12-14", sexeFemme,
				"annie.bonnet@mail.com", "0603010048", "16 rue de Verdun", null, "annie",
				passwordEncoder.encode("annie"), "2018-02-15", null, Paris20, null, droitsUtilisateur);

		// abonnement
		Abonnement abonnement1 = insertAbonnement(null, "2018-03-03", null, utilisateur1, utilisateur4);
		Abonnement abonnement2 = insertAbonnement(null, "2018-03-03", null, utilisateur4, utilisateur1);
		Abonnement abonnement3 = insertAbonnement(null, "2018-03-03", null, utilisateur2, utilisateur3);
		Abonnement abonnement4 = insertAbonnement(null, "2018-03-03", null, utilisateur3, utilisateur1);
		Abonnement abonnement5 = insertAbonnement(null, "2018-03-03", null, utilisateur3, utilisateur4);
		Abonnement abonnement6 = insertAbonnement(null, "2018-03-03", null, utilisateur4, utilisateur2);
		Abonnement abonnement7 = insertAbonnement(null, "2018-03-03", null, utilisateur5, utilisateur3);

		// création theme
		insertTheme(null, "Musique", "Musique");
		insertTheme(null, "Peinture", "Peinture");
		insertTheme(null, "Art plastique", "Art plastique");
		insertTheme(null, "Danse", "Danse");
		insertTheme(null, "Sculture", "Sculture");
		insertTheme(null, "Cinema", "Cinema");
		insertTheme(null, "Théatre", "Théatre");

		// publication
		insertPublication(null, "Joconde", "Portrait de Mona Lisa de l'artiste Léonard de Vinci ", "2020-06-12",
				utilisateur1);
		addThemePub(1, 2);
		insertSupport(null, "Image", "Joconde.jpg");
		addSupportPub(1, 1);

		insertPublication(null, "Le Radeau de La Méduse",
				"Le Radeau de La Méduse est une peinture à l'huile sur toile, réalisée entre 1818 et 1819 par le peintre et lithographe romantique français Théodore Géricault",
				"2021-01-15", utilisateur1);
		addThemePub(2, 2);
		insertSupport(null, "Image", "radeau-meduse.jpg");
		addSupportPub(2, 2);

		insertPublication(null, "La Liberté", "La Liberté guidant le peuple est une huile sur toile d'Eugène Delacroix",
				"2019-05-22", utilisateur2);
		addThemePub(3, 2);
		insertSupport(null, "Image", "liberte.png");
		addSupportPub(3, 3);

		insertPublication(null, "Vénus de Milo",
				"La Vénus de Milo est une statue en marbre représentant vraisemblablement la déesse Aphrodite",
				"2019-12-20", utilisateur2);
		addThemePub(4, 5);
		insertSupport(null, "Image", "venus.jpg");
		addSupportPub(4, 4);

		insertPublication(null, "Le Penseur",
				"Le Penseur est l'une des plus célèbres sculptures en bronze d'Auguste Rodin. Elle représente un homme en train de méditer",
				"2020-03-15", utilisateur3);
		addThemePub(5, 5);
		insertSupport(null, "Image", "penseur.jpg");
		addSupportPub(5, 5);

		insertPublication(null, "LA STATUE DE LA LIBERTÉ",
				"Considérée comme l'une des sculptures les plus célèbres du monde, la Statue de la Liberté a été offerte aux États-Unis par le peuple français",
				"2019-01-24", utilisateur4);
		addThemePub(6, 5);
		insertSupport(null, "Image", "statu-liberte.jpg");
		addSupportPub(6, 6);

		insertPublication(null, "Cyrano de Bergerac",
				"A cause de son nez, Cyrano n'ose songer à séduire Roxane. Mais puisqu'elle aime Christian, pourquoi ne pas tenter une expérience ?",
				"2019-06-04", utilisateur4);
		addThemePub(7, 7);

		insertPublication(null, "Les Nénufars",
				"Cette peinture est une séquence d’environ 250 peintures à l’huile. La toile a été réalisée par le très populaire impressionniste Claude Monet",
				"2020-05-04", utilisateur4);
		addThemePub(8, 2);
		insertSupport(null, "Image", "nenufars.jpg");
		addSupportPub(8, 7);

		insertPublication(null, "Revolver",
				"Entre pop anglaise et psychédélisme, les Beatles enregistrent un chef d'oeuvre absolu", "2020-07-26",
				utilisateur4);
		addThemePub(9, 1);
		insertSupport(null, "Image", "Revolver.jpg");
		addSupportPub(9, 8);

		// rajout support cyrano sur la publication cyrano
		insertSupport(null, "Image", "cyrano.jpg");
		addSupportPub(7, 9);

		insertPublication(null, "Ludwig van Beethoven",
				"L’année 1802 marque un premier grand tournant dans la vie du compositeur. Souffrant d'acouphènes, il commence en effet depuis 1796 à prendre conscience d’une surdité qui devait irrémédiablement progresser jusqu’à devenir totale avant 1820.",
				"2020-07-26", utilisateur1);
		addThemePub(10, 1);
		insertSupport(null, "Image", "beethoven.jpg");
		addSupportPub(10, 10);

		insertPublication(null, "Mozart",
				"Wolfgang Amadeus Mozart est un compositeur autrichien classique né à Salzbourg le 27 janvier 1756 et mort à Vienne le 5 décembre 1791",
				"1998-05-22", utilisateur3);
		addThemePub(11, 1);

		insertPublication(null, "The Rolling Stones",
				"The Rolling Stones est un groupe britannique de rock originaire de Londres, en Angleterre. Il est formé en 1962 par le guitariste et leader original Brian Jones, le pianiste Ian Stewart, le chanteur Mick Jagger et le guitariste Keith Richards.",
				"2005-01-17", utilisateur3);
		addThemePub(12, 1);

		insertPublication(null, "Harrison Ford",
				"Harrison Ford est un acteur américain, né le 13 juillet 1942 à Chicago. Sa collaboration avec George Lucas, le premier qui lui a donné l'occasion de s'affirmer en tant qu'acteur avec American Graffiti.",
				"2012-05-20", utilisateur2);
		addThemePub(13, 6);

		insertPublication(null, "L'Avare",
				"L'Avare est une comédie de Molière en cinq actes et en prose, adaptée de La Marmite de Plaute et représentée pour la première fois sur la scène du Palais-Royal le 9 septembre 1668",
				"2020-05-25", utilisateur5);
		addThemePub(14, 7);

		insertPublication(null, "Le Lac des cygnes",
				"Le Lac des cygnes est un ballet en quatre actes sur une musique de Piotr Ilitch Tchaïkovski et un livret de Vladimir Begitchev inspiré d'une légende allemande.",
				"2021-02-13", utilisateur5);
		addThemePub(15, 4);

		// annonce
		insertAnnonce(null, "Recherche modeles", 
				"Recherche model pour réaliser des portraits en peinture", 15.0,
				"2020-06-07", utilisateur4);
		addThemeAnnonce(1, 2);
		addSupportAnnonce(1, 1);

		insertAnnonce(null, "Fugurants", 
				"Je recherche des figurants pour cours métrages, sur l’époque de Napoléon. ",
				0.0, "2019-06-04", utilisateur5);
		addThemeAnnonce(2, 6);
		addSupportAnnonce(1, 2);

		insertAnnonce(null, "Appareil photo anciens", 
				"Je recherche d’ancien appareil photo argentique ", 0.0,
				"2021-02-15", utilisateur5);
		addThemeAnnonce(3, 6);
		addSupportAnnonce(1, 3);

		insertAnnonce(null, "Cour de piano", 
				"Je vous propose des cours de piano pour débutant", 15.0, "1998-05-22",
				utilisateur3);
		addThemeAnnonce(4, 1);
		
		insertAnnonce(null, "Cours de danse", "Je vous propose des cours de danse JAZZ", 12.0, "2020-09-15",
				utilisateur5);
		addThemeAnnonce(5, 4);
		
		insertAnnonce(null, "chorale", "chorale recherche personne pour cahnter avec nous", 0.0, "2021-03-02",
				utilisateur2);
		addThemeAnnonce(6, 1);
		
		insertAnnonce(null, "Matériel de cinéma", "Je vends de vieux projecteur de cinéma", 0.0, "2019-06-22",
				utilisateur1);
		addThemeAnnonce(7, 6);

	}

	private Ville insertVille(Long id, String codePostal, String localite, String pays) {
		Ville ville = new Ville();
		ville.setId(id);
		ville.setCodePostale(codePostal);
		ville.setLocalite(localite);
		ville.setPays(pays);
		return villeRepository.save(ville);
	}

	private void insertSupport(Long id, String typeSupport, String chemin) {
		Support support = new Support();
		support.setId(id);
		support.setTypeSupport(typeSupport);
		support.setChemin(chemin);
		supportRepository.save(support);
	}

	private void insertPublication(Long id, String titre, String texte, String dateDeDebut, Utilisateur utilisateur)
			throws ParseException {
		Publication publication = new Publication();
		publication.setId(id);
		publication.setTexte(texte);
		publication.setTitre(titre);
		if (dateDeDebut != null) {
			publication.setDateDebut(new SimpleDateFormat("yyyy-MM-dd").parse(dateDeDebut));
		} else {
			publication.setDateDebut(null);
		}
		publication.setUtilisateur(utilisateur);
		publicationRepository.save(publication);
	}

	private void insertTheme(Long id, String titre, String description) {
		Theme theme = new Theme();
		theme.setId(id);
		theme.setDescription(description);
		theme.setTitre(titre);
		themeRepository.save(theme);
	}

	private void addThemePub(long idPub, long idTheme) {
		Publication pub = publicationRepository.findById(idPub).get();
		Theme th = themeRepository.findById(idTheme).get();
		th.addPublication(pub);
		themeRepository.save(th);
	}

	private Droits insertDroits(Long id, String typeDroits) {
		Droits droits = new Droits();
		droits.setId(id);
		droits.setTypeDroit(typeDroits);
		return droitsRepository.save(droits);
	}

	private MotifResiliation insertMotifResiliation(Long id, String libelleMotif) {
		MotifResiliation motif = new MotifResiliation();
		motif.setId(id);
		motif.setLibelleMotift(libelleMotif);
		return motifResiliationRepository.save(motif);
	}

	private Sexe insertSexe(Long id, String nomSexe) {
		Sexe sexe = new Sexe();
		sexe.setId(id);
		sexe.setNonSexe(nomSexe);
		return sexeRepository.save(sexe);
	}

	private Utilisateur insertUtilisateur(Long id, String prenom, String nom, String dateDeNaissance, Sexe sexe,
			String mail, String telephone, String rue, String complement, String profile, String mdp,
			String dateInscription, String dateResiliation, Ville ville, MotifResiliation motifResiliation,
			Droits droits) throws ParseException {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setId(id);
		utilisateur.setNom(nom);
		utilisateur.setPrenom(prenom);
		if (dateDeNaissance != null) {
			utilisateur.setDateNais(new SimpleDateFormat("yyyy-MM-dd").parse(dateDeNaissance));
		} else {
			utilisateur.setDateNais(null);
		}

		utilisateur.setSexe(sexe);
		utilisateur.setMail(mail);
		utilisateur.setRue(rue);
		utilisateur.setComplement(complement);
		utilisateur.setTelephone(telephone);
		utilisateur.setProfile(profile);
		utilisateur.setPasseWord(mdp);
		if (dateInscription != null) {
			utilisateur.setDateInscription(new SimpleDateFormat("yyyy-MM-dd").parse(dateInscription));
		} else {
			utilisateur.setDateNais(null);
		}
		if (dateResiliation != null) {
			utilisateur.setDateResiliation(new SimpleDateFormat("yyyy-MM-dd").parse(dateResiliation));
		} else {
			utilisateur.setDateResiliation(null);
		}
		utilisateur.setVille(ville);
		utilisateur.setDroits(droits);
		utilisateur.setMotifResiliation(motifResiliation);
		return utilisateurRepository.save(utilisateur);

	}

	private Abonnement insertAbonnement(Long id, String dateDeDebut, String dateDeFin, Utilisateur following,
			Utilisateur follower) throws ParseException {
		Abonnement abo = new Abonnement();
		abo.setId(id);
		if (dateDeDebut != null) {
			abo.setDateDébut(new SimpleDateFormat("yyyy-MM-dd").parse(dateDeDebut));
		} else {
			abo.setDateDébut(null);
		}
		if (dateDeFin != null) {
			abo.setDateFin(new SimpleDateFormat("yyyy-MM-dd").parse(dateDeFin));
		} else {
			abo.setDateFin(null);
		}
		abo.setFollowing(following);
		abo.setFollower(follower);
		return abonnementRepository.save(abo);
	}

	private void addSupportPub(long idPub, long idSupport) {
		Publication pub = publicationRepository.findById(idPub).get();
		Support s = supportRepository.findById(idSupport).get();
		s.setPublicationSupport(pub);
		supportRepository.save(s);
	}

	private void insertAnnonce(Long id, String titre, String texte, Double prix, String dateParution,
			Utilisateur utilisateur) throws ParseException {
		Annonce annonce = new Annonce();
		annonce.setId(id);
		annonce.setTexte(texte);
		annonce.setTitre(titre);
		annonce.setPrix(prix);
		if (dateParution != null) {
			annonce.setDateParution(new SimpleDateFormat("yyyy-MM-dd").parse(dateParution));
		} else {
			annonce.setDateParution(null);
		}
		annonce.setUtilisateur(utilisateur);
		annonceRepository.save(annonce);
	}

	private void addThemeAnnonce(long idAnnonce, long idTheme) {
		Annonce an = annonceRepository.findById(idAnnonce).get();
		Theme th = themeRepository.findById(idTheme).get();
		th.addAnnonce(an);
		themeRepository.save(th);
	}

	private void addSupportAnnonce(long idAnnonce, long idSupport) {
		Annonce a = annonceRepository.findById(idAnnonce).get();
		Support s = supportRepository.findById(idSupport).get();
		s.setAnnonceSupport(a);
		supportRepository.save(s);
	}
}