Vue.createApp({
    data() {
        return {
            clientInfo: {},
            creditCards: [],
            debitCards: [],
            activeCards: [],
            cardToDelete: null,
            errorToats: null,
            errorMsg: null,
        }
    },
    methods: {
        getData: function () {
            axios.get("/api/clients/current")
                .then((response) => {
                    //get client ifo
                    this.clientInfo = response.data;
                    this.activeCards = this.clientInfo.cards.filter(card => card.deleted == false)
                    this.creditCards = this.activeCards.filter(card => card.type == "CREDIT" );
                    this.debitCards = this.activeCards.filter(card => card.type == "DEBIT");
                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        openDeleteModal: function (card){
            this.cardToDelete = card
            this.modal.show();
        },
        deleteCard: function () {
            const cardId = this.cardToDelete.id
            axios.patch(`/api/clients/current/cards/${cardId}`)
                .then(response => {
                    this.modal.hide();
                    this.okmodal.show();
                })
                .catch(() => {
                    this.errorMsg = "Error deleting card"
                    this.errorToats.show();
                })
        },
        finished: function () {
            window.location.reload();
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.modal = new bootstrap.Modal(document.getElementById('confirModal'));
        this.okmodal = new bootstrap.Modal(document.getElementById('okModal'));
        this.getData();
    }
}).mount('#app')