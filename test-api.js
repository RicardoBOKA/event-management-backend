// Script de test pour vérifier la connexion à Supabase via l'API REST
const axios = require('axios');

const API_BASE_URL = 'http://localhost:8080/v1';

async function testEventCreation() {
    console.log('=== TEST DE CONNEXION SUPABASE VIA API REST ===\n');
    
    try {
        // 1. Créer un utilisateur de test
        console.log('1. Création d\'un utilisateur de test...');
        const userData = {
            userName: 'test_organizer_api',
            email: 'test_api@example.com',
            password: 'password123'
        };
        
        const userResponse = await axios.post(`${API_BASE_URL}/users`, userData);
        const userId = userResponse.data.userId;
        console.log(`✅ Utilisateur créé avec l'ID: ${userId}\n`);
        
        // 2. Créer un événement de test
        console.log('2. Création d\'un événement de test...');
        const eventData = {
            eventName: 'Test Event API - Connexion Supabase',
            createdDate: new Date().toISOString().slice(0, 19).replace('T', ' '),
            startEvent: new Date(Date.now() + 24 * 60 * 60 * 1000).toISOString().slice(0, 19).replace('T', ' '),
            endEvent: new Date(Date.now() + 24 * 60 * 60 * 1000 + 2 * 60 * 60 * 1000).toISOString().slice(0, 19).replace('T', ' '),
            location: 'PARIS',
            description: 'Événement de test pour vérifier la connexion à Supabase via API',
            organizerId: userId
        };
        
        const eventResponse = await axios.post(`${API_BASE_URL}/events`, eventData);
        const eventId = eventResponse.data.eventId;
        console.log(`✅ Événement créé avec l'ID: ${eventId}`);
        console.log(`   Nom: ${eventResponse.data.eventName}`);
        console.log(`   Lieu: ${eventResponse.data.location}`);
        console.log(`   Organisateur: ${eventResponse.data.user.userId}\n`);
        
        // 3. Récupérer l'événement créé
        console.log('3. Récupération de l\'événement depuis la base de données...');
        const getEventResponse = await axios.get(`${API_BASE_URL}/events/${eventId}`);
        console.log(`✅ Événement récupéré: ${getEventResponse.data.eventName}\n`);
        
        // 4. Lister tous les événements
        console.log('4. Récupération de tous les événements...');
        const allEventsResponse = await axios.get(`${API_BASE_URL}/events`);
        console.log(`✅ Nombre total d'événements: ${allEventsResponse.data.length}`);
        
        // 5. Rechercher l'événement dans la liste
        const eventFound = allEventsResponse.data.find(event => event.eventId === eventId);
        if (eventFound) {
            console.log(`✅ L'événement de test a été trouvé dans la liste\n`);
        }
        
        console.log('=== TEST RÉUSSI ===');
        console.log('✅ La connexion à Supabase fonctionne correctement !');
        console.log('✅ L\'API REST fonctionne correctement !');
        console.log('✅ Les données sont bien sauvegardées et récupérées de la base de données !');
        
        // Nettoyage (optionnel)
        console.log('\n5. Nettoyage des données de test...');
        try {
            await axios.delete(`${API_BASE_URL}/events/${eventId}`);
            await axios.delete(`${API_BASE_URL}/users/${userId}`);
            console.log('✅ Données de test supprimées');
        } catch (cleanupError) {
            console.log('⚠️  Erreur lors du nettoyage (normal si les endpoints DELETE ne sont pas implémentés)');
        }
        
    } catch (error) {
        console.error('❌ ERREUR LORS DU TEST:');
        if (error.response) {
            console.error(`   Status: ${error.response.status}`);
            console.error(`   Message: ${error.response.data}`);
        } else if (error.request) {
            console.error('   Problème de connexion - Vérifiez que le serveur est démarré sur le port 8080');
        } else {
            console.error(`   Erreur: ${error.message}`);
        }
        process.exit(1);
    }
}

// Exécuter le test
testEventCreation();
