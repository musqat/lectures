import "../styles/global.css";

import { Metadata } from "next"
import Navigation from "../components/navigation"

export const metadata :Metadata = {
  title: {
    template : "%s | Next Movies",
    default : "Next Movies"
  },
    description : 'The Best Moives on the best framework',
}

export default function Layout({ 
    children 
}: {
     children: React.ReactNode
     }) {
  return (
    <html>
      <body>
        <Navigation/>
        {children}
        </body>
    </html>
  )
}
