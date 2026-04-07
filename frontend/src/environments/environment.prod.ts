/**
 * Production environment configuration for the CMS frontend.
 * Update keycloak.url and apiUrl to match the production deployment.
 */
export const environment = {
  production: true,
  keycloak: {
    url: 'https://auth.cms.college.edu',
    realm: 'cms',
    clientId: 'cms-frontend',
  },
  apiUrl: '/api/v1',
};
