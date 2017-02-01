import { FitnesseConnectUiPage } from './app.po';

describe('fitnesse-connect-ui App', function() {
  let page: FitnesseConnectUiPage;

  beforeEach(() => {
    page = new FitnesseConnectUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
