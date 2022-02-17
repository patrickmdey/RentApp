export const LOGIN_MOCKED_DATA = {
    EMAIL: 'pdey@itba.edu.ar',
    TOKEN:
        'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZGV5QGl0YmEuZWR1LmFyLDUiLCJleHAiOjE2NDU2NjQ1NDV9.NGUe8Bnz5uKmr5vQ3u2iCqTfXx04WA-UKJaTbggmH9k',
    get USER() {
        return {
            'firstName': 'Patrick',
            'lastName': 'Dey',
            'email': this.EMAIL,
            'owner': true,
            'pendingRequestAmount': 0,
            'acceptedRequestAmount': 0,
            'declinedRequestAmount': 0,
            'url': 'http://localhost:8080/api/users/39',
            'imageUrl': 'http://localhost:8080/api/images/117',
            'locationUrl': 'http://localhost:8080/api/locations/PALERMO',
            'id': 1,
        };
    }
}