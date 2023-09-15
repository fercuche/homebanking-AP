Vue.createApp({
    data() {
        return {
            accountInfo: {},
            startDate: '',
            endDate: '',
            transactionFiltered:{},
            errorToats: null,
            errorMsg: null,
        }
    },
    methods: {
        getData: function () {
            const urlParams = new URLSearchParams(window.location.search);
            const id = urlParams.get('id');
            axios.get(`/api/accounts/${id}`)
                .then((response) => {
                    //get client ifo
                    this.accountInfo = response.data;
                    this.accountInfo.transactions.sort((a, b) => (b.id - a.id))
                })
                .catch((error) => {
                    // handle error
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        filterDates: function (){
            this.modal.show();
        },
        filteredTransactions:function (){
            axios.get(`/api/transactions?fromDate=${this.startDate}&toDate=${this.endDate}`)
                .then((response) => {
                    this.transactionFiltered = response.data;
                    this.modal.hide();
                    this.filterModal.show();
                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                });
            this.transactionFiltered = {};

        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
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
        this.modal = new bootstrap.Modal(document.getElementById('filterModal'));
        this.filterModal = new bootstrap.Modal(document.getElementById('filteredModal'));
        this.getData();
    }
}).mount('#app')