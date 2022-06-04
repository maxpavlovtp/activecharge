import React, { Suspense } from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import MainScreen from "./App/mainScreen/MainScreen";
import ContactsScreen from "./App/contactsScreen/ContactsScreen";
import ContractScreen from "./App/contractScreen/ContractScreen";
import PaymentScreen from "./App/paymentScreen/PaymentScreen";
import ChargingScreen from "./App/chargingScreen/ChargingScreen";
import OverloadPage from "./components/overload-page/OverloadPage";

function App() {
  return (
    <Suspense fallback={null}>
      <Router>
        <Routes>
          <Route path="/contract" element={<ContractScreen />} />
          <Route path="/contacts" element={<ContactsScreen />} />
          <Route path="/payment" element={<PaymentScreen />} />
          <Route path="/charging" element={<ChargingScreen />} />
          <Route path="/overload" element={<OverloadPage />} />
          <Route path="/" element={<MainScreen />} />
        </Routes>
      </Router>
    </Suspense>
  );
}

export default App;
