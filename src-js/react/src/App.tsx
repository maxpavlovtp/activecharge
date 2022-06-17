import React, { Suspense } from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import MainScreen from "./App/mainScreen/MainScreen";
import ContactsScreen from "./App/contactsScreen/ContactsScreen";
import ContractScreen from "./App/contractScreen/ContractScreen";
import PaymentScreen from "./App/paymentScreen/PaymentScreen";
import ChargingScreen from "./App/chargingScreen/ChargingScreen";
import MainOverload from "./components/overload-page/MainOverload";
import PrivateRoute from "./components/privateRoute/PrivateRoute";

function App() {
  return (
    <Suspense fallback={null}>
      <Router>
        <Routes>
          <Route path="/contract" element={<ContractScreen />} />
          <Route path="/contacts" element={<ContactsScreen />} />
          <Route path="/payment" element={<PaymentScreen />} />
          <Route path="/charging" element={<ChargingScreen />} />
          <Route path="/overload" element={<MainOverload />} />
          <Route
            path="/"
            element={
              <PrivateRoute>
                <MainScreen />
              </PrivateRoute>
            }
          />
        </Routes>
      </Router>
    </Suspense>
  );
}

export default App;
