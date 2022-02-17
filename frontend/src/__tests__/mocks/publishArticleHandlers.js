import { rest } from 'msw';
import { Article } from '../../api/articles/types';

let article = {
	title: '',
	description: '',
	pricePerDay: 0,
	timesRented: 0,
	rating: 0,
	timesReviewed: 0,
	id: 5,
	url: 'http://localhost:8080/articles/5',
	ownerUrl: 'http://localhost:8080/users/39',
	imagesUrl: 'http://localhost:8080/images?fromArticle=5',
	categoriesUrl: 'http://localhost:8080/categories?fromArticle=5',
	reviewsUrl: 'http://localhost:8080/articles/5'
};

export const publishArticleHandlers = [
	rest.post('/articles', (req, res, ctx) => {
		if (req.bodyUsed && req.body.title && req.body.description && req.body.pricePerDay) {
			article.title = req.body.title;
			article.description = req.body.description;
			article.pricePerDay = req.body.pricePerDay;
			return res(ctx.status(200), ctx.json({ url: 'http://localhost:8080/articles/5' }));
		} else return res(ctx.status(400));
	}),

	rest.get('/articles/:id', (req, res, ctx) => {
		const { id } = req.params;

		return res(ctx.status(200), ctx.json(article));
	}),

	rest.put('/articles/:id', (req, res, ctx) => {
		const { id } = req.params;
		if (id === article.id && req.bodyUsed && req.body.title && req.body.description && req.body.pricePerDay) {
			article.title = req.body.title;
			article.description = req.body.description;
			article.pricePerDay = req.body.pricePerDay;
			return res(ctx.status(200));
		} else return res(ctx.status(400));
	}),

	rest.put('/proposals/:id', (req, res, ctx) => {
		return res(ctx.status(200));
	}),

	rest.get('/locations', (req, res, ctx) => {
		const token = req.headers.get('Authorization');
		if (token !== undefined && token?.includes('Basic ')) {
			sessionStorage.setItem('is-authenticated', 'true');
			ctx.set('Authorization', 'Bearer '); //aca poner el token que generaria jwt supongo en base64
		}
		return res(
			ctx.status(200),
			ctx.json([{ id: 'RETIRO', name: 'Retiro', url: 'http://localhost:8080/locations/RETIRO' }])
		);
	})
];
