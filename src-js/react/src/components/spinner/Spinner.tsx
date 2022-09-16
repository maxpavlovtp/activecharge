import React from "react";
import "./Spinner.css";
import { IoEarthOutline } from "react-icons/io5";

export default function Spinner() {
  return (
    <div className="loadAnimation">
      <IoEarthOutline className="animation" />
    </div>
  );
}
