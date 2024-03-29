import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import "bootstrap/dist/css/bootstrap.min.css";
import {
  CacheLocation,
  FpjsProvider,
} from "@fingerprintjs/fingerprintjs-pro-react";

import "./i18n";
import { Provider } from "react-redux";
import { setupStore } from "./store/store";

const store = setupStore();

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <FpjsProvider
    cacheTimeInSeconds={86399}
    cacheLocation={CacheLocation.LocalStorage}
    loadOptions={{
      apiKey: `${process.env.REACT_APP_FINGERPRINT_ID}`,
      region: "eu",
    }}
  >
    <Provider store={store}>
      <App />
    </Provider>
  </FpjsProvider>
);

reportWebVitals();
