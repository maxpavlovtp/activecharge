import React, { Suspense } from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Layout from "./components/layout/Layout";
import NotFoundPage from "./components/notFoundPage/NotFoundPage";
import PrivateRoute from "./components/privateRoute/PrivateRoute";
import HomeScreen from "./App/homeScreen/HomeScreen";
import MainSection from "./App/mainScreen/MainSection";
import ChargingSection from "./App/chargingScreen/ChargingSection";
import ContactsSection from "./App/contactsScreen/ContactsSection";
import ContractSection from "./App/contractScreen/ContractSection";

function App() {
  return (
    <Suspense fallback={null}>
      <Router>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route path="*" element={<NotFoundPage />} />
            <Route path="/contract" element={<ContractSection />} />
            <Route path="/contacts" element={<ContactsSection/>} />
            <Route path="/charging" element={<ChargingSection />} />
            <Route
              path="/start"
              element={
                <PrivateRoute>
                  <MainSection />
                </PrivateRoute>
              }
            />
            <Route path="/" element={<HomeScreen />} />
          </Route>
        </Routes>
      </Router>
    </Suspense>
  );
}

export default App;
