describe('API tests', () => {
    it('Get user', () => {
        cy.request("GET", 'http://localhost:8080/api/v1/users/1').then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body.id).to.eq(1);
            expect(response.body.firstName).to.eq('John');
            expect(response.body.lastName).to.eq('Parker');
            expect(response.body.email).to.eq('jp@email.com');
        });
    })

    it('Update user', () => {
        cy.request("PUT", 'http://localhost:8080/api/v1/users/11', {
            "id": 11,
            "firstName": "test1",
            "lastName": "test2",
            "email": "test@email.com"
        }).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body.id).to.eq(11);
            expect(response.body.firstName).to.eq('test1');
            expect(response.body.lastName).to.eq('test2');
            expect(response.body.email).to.eq('test@email.com');
        });
    })
})