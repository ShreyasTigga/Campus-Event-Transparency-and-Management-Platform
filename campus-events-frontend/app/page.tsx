import Link from "next/link";

export default function Home() {
  return (
    <div className="h-screen flex flex-col justify-center items-center">
      <h1 className="text-4xl font-bold mb-6">
        Campus Event Transparency Platform
      </h1>

      <div className="flex gap-4">
        <Link href="/login" className="bg-blue-500 text-white px-6 py-2 rounded">
          Login
        </Link>
        <Link
          href="/register"
          className="bg-green-500 text-white px-6 py-2 rounded"
        >
          Register
        </Link>
      </div>
    </div>
  );
}
