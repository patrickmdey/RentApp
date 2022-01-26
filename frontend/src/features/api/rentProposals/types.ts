import { Url } from 'url';

type RentProposalState = 0 | 1 | 2;

export interface RentProposal {
	message: String;
	startDate: String;
	endDate: String;
	seen: Boolean;
	state: RentProposalState;
	url: Url;
	articleUrl: Url;
	renterUrl: Url;
}

export interface ListRentProposalsParameters {
	// TODO: ver como se divide en enviadas y recibidas
	userId: Number; // TODO: esto tambien seria parte del token
	page?: Number;
}

export interface CreateRentProposalParameters {
	message: String;
	startDate: String;
	endDate: String;
	articleId: Number;
	renterId: Number; // TODO: esto seria el token de auth en realidad
}

export interface UpdateRentProposalParameters {
	state: RentProposalState;
	url: URL;
}
