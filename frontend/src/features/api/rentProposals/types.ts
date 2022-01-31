
type RentProposalState = 0 | 1 | 2;

export interface RentProposal {
	message: string;
	startDate: string;
	endDate: string;
	seen: boolean;
	state: RentProposalState;
	url: URL;
	articleUrl: URL;
	renterUrl: URL;
}

export interface ListRentProposalsParameters {
	// TODO: ver como se divide en enviadas y recibidas
	userId: Number; // TODO: esto tambien seria parte del token
	page?: Number;
}

export interface CreateRentProposalParameters {
	message: string;
	startDate: string;
	endDate: string;
	articleId: Number;
	renterId: Number; // TODO: esto seria el token de auth en realidad
}

export interface UpdateRentProposalParameters {
	state: RentProposalState;
	url: URL;
}
