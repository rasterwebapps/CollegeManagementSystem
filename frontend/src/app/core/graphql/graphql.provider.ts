import { Apollo, APOLLO_OPTIONS } from 'apollo-angular';
import { HttpLink } from 'apollo-angular/http';
import { InMemoryCache, ApolloLink } from '@apollo/client/core';
import { setContext } from '@apollo/client/link/context';
import { inject, Provider } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { environment } from '../../../environments/environment';

const graphqlUri = environment.apiUrl.replace('/api/v1', '/graphql');

export function provideGraphQL(): Provider[] {
  return [
    Apollo,
    {
      provide: APOLLO_OPTIONS,
      useFactory: () => {
        const httpLink = inject(HttpLink);
        const authService = inject(AuthService);

        const http = httpLink.create({ uri: graphqlUri });

        const auth = setContext(async () => {
          if (!authService.authenticated()) {
            return {};
          }
          const token = await authService.updateToken(30);
          return {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          };
        });

        return {
          link: ApolloLink.from([auth, http]),
          cache: new InMemoryCache(),
          defaultOptions: {
            watchQuery: {
              fetchPolicy: 'cache-and-network',
            },
          },
        };
      },
    },
  ];
}
